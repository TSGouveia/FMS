package org.cts.modules.product;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import org.cts.utilities.Constants;

public class REQUESTExecuteTransportInitiator extends AchieveREInitiator {

    public REQUESTExecuteTransportInitiator(Agent a, ACLMessage msg) {
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
        if(((ProdutAgent) myAgent).myProcess.size() != 0){
            ((ProdutAgent) myAgent).myLocation = inform.getContent();
            //Execute Skill
            //Add Execute Skill Behaviour
            //Create message
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(((ProdutAgent) myAgent).resourceExecuter);
            msg.setContent(((ProdutAgent) myAgent).myProcess.get(0));
            msg.setOntology(Constants.ONTOLOGY_REQUEST_EXECUTE_SKILL);
            //Add behaviour
            ((ProdutAgent) myAgent).addBehaviour(new REQUESTExecuteSkillInitiator(myAgent,msg));
        }else{
            System.out.println("DEBUG: " + myAgent.getLocalName() + " LEAVING SYSTEM");
            myAgent.doDelete();
        }
    }
}
