package org.cts.modules.resource;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import org.cts.modules.resource.libraries.human_library;
import org.cts.modules.resource.libraries.kitt_library;
import org.cts.modules.resource.libraries.robot_library;
import org.cts.utilities.Constants;

import java.io.IOException;

public class REQUESTExecuteSkillResponder extends AchieveREResponder {

    public REQUESTExecuteSkillResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Received REQUEST execute skill");
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    }

    private void executeSkill(String content) throws IOException {
        if(((ResourceAgent) myAgent).myResourceType.matches(Constants.ResourceType.Station.name())){
            //Call arduino service
            if(content.matches("Skill_Screw")){
                kitt_library.executeStation("0");
            } else {
                kitt_library.executeStation("1");
            }
        } else if (((ResourceAgent) myAgent).myResourceType.matches(Constants.ResourceType.Robot.name())) {
            //Call ROS service
            robot_library.executeSkill(content, ((ResourceAgent) myAgent).myLocation);
        } else if (((ResourceAgent) myAgent).myResourceType.matches(Constants.ResourceType.Human.name())) {
            //Call human service
            human_library.executeSkill(content);
        }
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println("DEBUG: " + myAgent.getLocalName() + " Executing skill");
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.INFORM);

        try {
            executeSkill(request.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //System.err.println("Executei skill: " + request.getContent());
        return reply;
    }
}
