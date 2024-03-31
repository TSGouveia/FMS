package org.cts.modules.product;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;

import java.util.Vector;

public class CNETProposalInitiator extends ContractNetInitiator {
    public CNETProposalInitiator(Agent a, ACLMessage cfp) {
        super(a, cfp);
    }

    @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Processing proposals");
        int bestProposalValue;
        int bestProposalIndex;
        if(((ProdutAgent)myAgent).myPriority.matches("Robot")){
            bestProposalValue = 99;
            bestProposalIndex = -1;
            for (int i = 0; i < responses.size(); i++) {
                if(Integer.parseInt(((ACLMessage) responses.get(i)).getContent()) < bestProposalValue){
                    bestProposalValue = Integer.parseInt(((ACLMessage) responses.get(i)).getContent());
                    bestProposalIndex = i;
                }
                ACLMessage reply = ((ACLMessage) responses.get(i)).createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.add(reply);
            }
        }else{
            bestProposalValue = -1;
            bestProposalIndex = 99;
            for (int i = 0; i < responses.size(); i++) {
                if(Integer.parseInt(((ACLMessage) responses.get(i)).getContent()) > bestProposalValue){
                    bestProposalValue = Integer.parseInt(((ACLMessage) responses.get(i)).getContent());
                    bestProposalIndex = i;
                }
                ACLMessage reply = ((ACLMessage) responses.get(i)).createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.add(reply);
            }
        }
        ((ACLMessage) acceptances.get(bestProposalIndex)).setPerformative(ACLMessage.ACCEPT_PROPOSAL);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + "Received inform from CNET");
        ((ProdutAgent)myAgent).nextLocation = inform.getContent();
        //Add Transport Behaviour
        //Create message
        DFAgentDescription[] dfAgentDescriptions = new DFAgentDescription[0];
        try {
            dfAgentDescriptions = DFInteraction.searchInDFByName(myAgent, Constants.DF_SERVICE_NAME_TRANSPORT);
            if(dfAgentDescriptions.length==0)
            {
                System.out.println("DEBUG: " + myAgent.getLocalName() + "Transport not found in DF");
                ((ProdutAgent) myAgent).addBehaviour(new NextSkillBehaviour());
                return;
            }
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent(((ProdutAgent) myAgent).nextLocation);
            msg.addReceiver(dfAgentDescriptions[0].getName());
            msg.setOntology(Constants.ONTOLOGY_REQUEST_TRANSPORT);
            //Add behaviour
            ((ProdutAgent) myAgent).addBehaviour(new REQUESTExecuteTransportInitiator(myAgent,msg));
            ((ProdutAgent) myAgent).resourceExecuter = inform.getSender();
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
