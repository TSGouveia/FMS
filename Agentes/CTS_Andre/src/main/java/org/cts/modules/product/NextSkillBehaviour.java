package org.cts.modules.product;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;

public class NextSkillBehaviour extends OneShotBehaviour {

    @Override
    public void action() {
        //GetNextSkill
        String nextSkill = ((ProdutAgent) myAgent).myProcess.get(0);
        //LaunchCFP
        try {
            //Create message
            DFAgentDescription[] dfAgentDescriptions = DFInteraction.searchInDFByName(myAgent, nextSkill);
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            msg.setContent(nextSkill);
            if(dfAgentDescriptions.length!=0){
                for (int i = 0; i < dfAgentDescriptions.length; i++) {
                    msg.addReceiver(dfAgentDescriptions[i].getName());
                }
                msg.setOntology(Constants.ONTOLOGY_NEGOTIATE_NEXT_RESOURCE);
                //Add behaviour
                ((ProdutAgent) myAgent).addBehaviour(new CNETProposalInitiator(myAgent,msg));
            } else {
                ((ProdutAgent) myAgent).addBehaviour(new NextSkillBehaviour());
            }

        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
