package org.cts.modules.deployment;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.cts.modules.deployment.server.HTTPServer;
import org.cts.modules.deployment.server.SystemCurrentStatus;
import org.cts.modules.product.ProdutAgent;
import org.cts.modules.resource.ResourceAgent;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DeploymentAgent extends Agent implements fromServerToAgent{

    int productID = 0;

    static SystemCurrentStatus mySystem = new SystemCurrentStatus();
    //ArrayList<String> pluggedResources = new ArrayList<>();
    //String productLocation = "A";

    public static DeploymentAgent deployment;

    @Override
    protected void setup() {
        DeploymentAgent.deployment = this;
        SpringApplication.run(HTTPServer.class);
        this.mySystem.plugResource("Station", "D");
        try {
            deployResourceAgent("Station",new ArrayList<>(),"Station","D");
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
        this.addBehaviour(new DetectRobotBehaviour(this,5000));

    }

    public void deployResourceAgent(String myName, ArrayList<String> mySkills, String myResourceType, String myLocation) throws StaleProxyException {
        //Arguments
        //1 - List of Skills - ArrayList with Strings
        //2 - Resource Type - String ()
        //3 - Location
        ResourceAgent newResourceAgent = new ResourceAgent();
        newResourceAgent.setArguments(new Object[]{mySkills, myResourceType, myLocation});
        AgentController agent = this.getContainerController().acceptNewAgent(myName, newResourceAgent);
        agent.start();
        sendUpdateHMI();
    }

    public void deployProductAgent(ArrayList<String> myProcess, String myPriority, String myLocation) throws StaleProxyException {
        //Arguments
        //1 - List of Skills - ArrayList with Strings
        //2 - Priority - String (Robot, Human)
        //3 - Location - String
        ProdutAgent newProductAgent = new ProdutAgent();
        newProductAgent.setArguments(new Object[]{myProcess, myPriority, myLocation});
        AgentController agent = this.getContainerController().acceptNewAgent("Product_" + ++productID, newProductAgent);
        agent.start();
    }

    @Override
    public void receivedNewProduct(ArrayList<String> myProcess, String myPriority, String myLocation) {
        try {
            deployProductAgent(myProcess,myPriority,myLocation);
            System.out.println("DEBUG: NEW PRODUCT DEPLOYED - " + myProcess.iterator().next());
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void plugOperator(String operatorID, String location) {
        this.mySystem.plugResource(operatorID, location);
        try {
            this.deployResourceAgent(operatorID, new ArrayList<>(), Constants.ResourceType.Human.name(),location);
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }}

    @Override
    public void unplugOperator(String operatorID, String location) {
        this.mySystem.unplugResource(operatorID);
        //Start behaviour to kill Resource Agent
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(operatorID, false));
        msg.setOntology(Constants.ONTOLOGY_REQUEST_UNPLUG_RESOURCE);
        this.addBehaviour(new REQUESTUnplugResourceInitiator(this, msg));
    }

    @Override
    public void updateProductPosition(String position) {
        mySystem.setProductLocation(position);
        sendUpdateHMI();
    }

    @Override
    public void updateConfiguration(String myConf) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setOntology(Constants.ONTOLOGY_REQUEST_UPDATE_CONFIG);
        msg.setContent(myConf);
        try {
            DFAgentDescription[] myResources = DFInteraction.searchInDFByType(this,Constants.DF_SERVICE_TYPE_RESOURCE);
            for (int i = 0; i < myResources.length; i++) {
                msg.addReceiver(myResources[i].getName());
            }
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
        this.addBehaviour(new REQUESTUpdateConfigInitiator(this,msg));
    }

    protected static void sendUpdateHMI() {
        System.out.println("DEBUG DEPLOYMENT AGENT SEND UPDATE TO HMI");
        System.out.println("Current status");
        Gson gson = new Gson();
        gson.toJson(mySystem);

        System.out.println(gson.toJson(mySystem));

        //Call service
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, gson.toJson(mySystem));

        Request request = new Request.Builder().url("http://" + "localhost" + ":8081/updateHMI") //alteramos coisas aqui!!!!!!
                .method("POST",body)
                .addHeader("Content-Type","application/json")
                .build();


            Response response = null;

            try {
            response = client.newCall(request).execute();
            response.body().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.headers());



    }

    public static void updateProLoc(String position){
        mySystem.setProductLocation(position);
        sendUpdateHMI();
    }
}
