package org.cts.modules.deployment;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class REQUESTUnplugResourceInitiator extends AchieveREInitiator {

    public REQUESTUnplugResourceInitiator(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received AGREE unplug resource");
        ((DeploymentAgent)myAgent).sendUpdateHMI();
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received INFORM unplug resource");
    }
}
