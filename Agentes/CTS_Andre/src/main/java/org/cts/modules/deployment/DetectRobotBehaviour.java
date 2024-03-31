package org.cts.modules.deployment;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.StaleProxyException;
import org.cts.modules.resource.libraries.kitt_library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class DetectRobotBehaviour extends TickerBehaviour {

    /**
     * Construct a <code>TickerBehaviour</code> that call its
     * <code>onTick()</code> method every <code>period</code> ms.
     *
     * @param a      is the pointer to the agent
     * @param period the tick period in ms
     */
    public DetectRobotBehaviour(Agent a, long period) {
        super(a, period);
    }

    static String ip = "192.168.2.28";

    boolean Robot1ToPlug = false, Robot2ToPlug = false;

    @Override
    protected void onTick() {
        //Call service
        try {
            //Call Service
            String plugged = DetectRobotBehaviour.executeVerification();
            //Check connected robots
            StringTokenizer st = new StringTokenizer(plugged, ";");
            Robot1ToPlug = false; Robot2ToPlug = false;
            if(st.nextToken().equals("1"))
                Robot1ToPlug = true;
            if(st.nextToken().equals("1"))
                Robot2ToPlug = true;

            HashMap<String, String> mySystem = ((DeploymentAgent) myAgent).mySystem.getPluggedResources();
            if(Robot1ToPlug && !mySystem.containsKey("RobotB")){
                ((DeploymentAgent) myAgent).mySystem.plugResource("RobotB", "B");
                ((DeploymentAgent) myAgent).deployResourceAgent("RobotB",new ArrayList<>(),"Robot","B");
            }
            if(!Robot1ToPlug && mySystem.containsKey("RobotB"))
                ((DeploymentAgent) myAgent).unplugOperator("RobotB","B");
            if(Robot2ToPlug && !mySystem.containsKey("RobotF")){
                ((DeploymentAgent) myAgent).mySystem.plugResource("RobotF", "F");
                ((DeploymentAgent) myAgent).deployResourceAgent("RobotF",new ArrayList<>(),"Robot","F");
            }
            if(!Robot2ToPlug && mySystem.containsKey("RobotF"))
                ((DeploymentAgent) myAgent).unplugOperator("RobotF","F");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }


        //Verify with plugged resources
        //Plug or unplug

    }

    public static String executeVerification() throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);

        Request request = new Request.Builder().url("http://192.168.2.28/sensores").build();
        try {
            kitt_library.kittBeingUsed.acquire();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Response response = client.newCall(request).execute();
        kitt_library.kittBeingUsed.release();
        MediaType contentType = null;
        String bodyString = null;
        if (response.body() != null) {
            contentType = response.body().contentType();
            bodyString = response.body().string();
        }
        response.body().close();
        return bodyString;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(DetectRobotBehaviour.executeVerification());
    }

}
