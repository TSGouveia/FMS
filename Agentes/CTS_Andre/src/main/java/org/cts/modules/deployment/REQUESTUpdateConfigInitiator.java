package org.cts.modules.deployment;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class REQUESTUpdateConfigInitiator extends AchieveREInitiator {

    public REQUESTUpdateConfigInitiator(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received AGREE update config");
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received INFORM update config");
    }
}
