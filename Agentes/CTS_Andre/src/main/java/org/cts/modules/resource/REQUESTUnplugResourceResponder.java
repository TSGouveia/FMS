package org.cts.modules.resource;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class REQUESTUnplugResourceResponder extends AchieveREResponder {

    public REQUESTUnplugResourceResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received REQUEST unplug resource");
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " RProcessing unplug resource");
        myAgent.doDelete();
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        return reply;
    }
}
