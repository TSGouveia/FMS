package org.cts.modules.product;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;

public class REQUESTExecuteSkillInitiator extends AchieveREInitiator {

    public REQUESTExecuteSkillInitiator(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received AGREE execute transport");
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received REFUSE execute transport");
        ((ProdutAgent) myAgent).addBehaviour(new NextSkillBehaviour());
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received INFORM execute transport");
        ((ProdutAgent) myAgent).myProcess.remove(0);
        if(((ProdutAgent) myAgent).myProcess.size()!=0) {
            ((ProdutAgent)myAgent).addBehaviour(new NextSkillBehaviour());
        }else{
            //Leave system (Transport to A)
            ((ProdutAgent)myAgent).nextLocation = Constants.Locations.A.name();
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
            } catch (FIPAException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
