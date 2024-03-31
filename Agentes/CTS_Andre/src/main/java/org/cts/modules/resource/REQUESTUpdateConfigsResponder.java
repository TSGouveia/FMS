package org.cts.modules.resource;

import com.google.gson.Gson;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import org.cts.modules.deployment.server.UpdateConfiguration;

import java.util.ArrayList;

public class REQUESTUpdateConfigsResponder extends AchieveREResponder {

    public REQUESTUpdateConfigsResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        Gson gson = new Gson();
        UpdateConfiguration myConf = gson.fromJson(request.getContent(), UpdateConfiguration.class);
        ArrayList<String> arrayList = myConf.getResources().get(myAgent.getLocalName());
        try {
            ((ResourceAgent)myAgent).updateSkills(arrayList);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
        return reply;
    }
}
