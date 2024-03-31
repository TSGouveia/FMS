package org.cts.modules.transport;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import org.cts.modules.deployment.DeploymentAgent;
import org.cts.modules.resource.libraries.kitt_library;
import org.cts.utilities.Constants;

import java.io.IOException;

public class REQUESTExecuteTransport extends AchieveREResponder {

    public REQUESTExecuteTransport(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received REQUEST execute transport");
        ACLMessage reply = request.createReply();
        if(validLocation(request.getContent())){
            System.out.println("DEBUG: " + myAgent.getLocalName() + " Valid location - Starting transport");
            reply.setPerformative(ACLMessage.AGREE);
        }
        else{
            System.out.println("DEBUG: " + myAgent.getLocalName() + " Invalid location - Transport refused");
            reply.setPerformative(ACLMessage.REFUSE);
        }
        return reply;
    }

    private boolean validLocation(String location) {
        if(location.matches(Constants.Locations.A.name()) ||
                location.matches(Constants.Locations.B.name()) ||
                location.matches(Constants.Locations.C.name()) ||
                location.matches(Constants.Locations.D.name()) ||
                location.matches(Constants.Locations.E.name()) ||
                location.matches(Constants.Locations.F.name())
        )
            return true;
        else
            return false;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Executing transport");
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        //EXECUTE TRANSPORT
        try {
            executeTransport(request.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Transport finished");
        return reply;
    }

    private void executeTransport(String futureLocation) throws IOException {
        Constants.Locations myLocations[] = Constants.Locations.values();

        int currentIndex = 0;
        for (int i = 0; i < myLocations.length; i++) {
            if(myLocations[i].name().matches(((TransportAgent) myAgent).currentProductLocation)){
                currentIndex = i;
                break;
            }
        }

        if(((TransportAgent) myAgent).currentProductLocation.equals(futureLocation)){
            System.out.println("Already in position: " + futureLocation);
            return;
        }

        while(true){
            if(myLocations[currentIndex].name().matches(futureLocation)){
                System.out.println("Arrived: " + futureLocation);
                ((TransportAgent) myAgent).currentProductLocation = futureLocation;
                return;
            }else{
                if(currentIndex+1>5){
                    System.out.println("DEBUG: " + " Call transport from " + myLocations[currentIndex].name() + " to " + myLocations[0].name());
                    //Execute move
                    kitt_library.executeTransporte(myLocations[currentIndex].name() + myLocations[0].name());
                    DeploymentAgent.updateProLoc(myLocations[0].name());
                    ((TransportAgent) myAgent).currentProductLocation = myLocations[0].name();
                    currentIndex=0;
                }else{
                    System.out.println("DEBUG: " + " Call transport from " + myLocations[currentIndex].name() + " to " + myLocations[currentIndex+1].name());
                    //Execute move
                    kitt_library.executeTransporte(myLocations[currentIndex].name() + myLocations[currentIndex+1].name());
                    DeploymentAgent.updateProLoc(myLocations[currentIndex+1].name());
                    ((TransportAgent) myAgent).currentProductLocation = myLocations[currentIndex+1].name();
                    currentIndex++;
                }
            }
        }
    }
}
