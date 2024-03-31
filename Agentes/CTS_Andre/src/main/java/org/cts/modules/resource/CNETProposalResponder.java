package org.cts.modules.resource;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import org.cts.utilities.Constants;

public class CNETProposalResponder extends ContractNetResponder {


    public CNETProposalResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Creating proposal");
        ACLMessage reply = cfp.createReply();
        reply.setPerformative(ACLMessage.PROPOSE);
        if(String.valueOf(Constants.ResourceType.Human).equals(((ResourceAgent)myAgent).myResourceType))
            reply.setContent("2");
        else
            reply.setContent("1");
        return reply;
    }

    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Proposal Accepted");
        ACLMessage reply = accept.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setContent(((ResourceAgent)myAgent).myLocation);
        return reply;
    }
}
