package org.cts.modules.resource;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;

import java.util.ArrayList;

public class ResourceAgent extends Agent {

    protected ArrayList<String> mySkills;
    protected String myResourceType;
    protected String myLocation;

    @Override
    protected void setup() {
        //Get Arguments
        //1 - List of Skills - ArrayList with Strings
        //2 - Resource Type - String ()
        //3 - Location
        mySkills = (ArrayList<String>) this.getArguments()[0];
        myResourceType = (String) this.getArguments()[1];
        myLocation = (String) this.getArguments()[2];

        //Register in DF
        DFInteraction.registerInDF(this, Constants.DF_SERVICE_TYPE_SKILL, mySkills);

        //Launch Responder Behaviours
        //Negotiation
        this.addBehaviour(new CNETProposalResponder(this,
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP),
                        MessageTemplate.MatchOntology(Constants.ONTOLOGY_NEGOTIATE_NEXT_RESOURCE))));
        //Request Skill
        this.addBehaviour(new REQUESTExecuteSkillResponder(this,
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology(Constants.ONTOLOGY_REQUEST_EXECUTE_SKILL))));

        //Request Unplug
        this.addBehaviour(new REQUESTUnplugResourceResponder(this,
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology(Constants.ONTOLOGY_REQUEST_UNPLUG_RESOURCE))));

        //Request Update Configs
        this.addBehaviour(new REQUESTUpdateConfigsResponder(this,
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology(Constants.ONTOLOGY_REQUEST_UPDATE_CONFIG))));
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSkills(ArrayList<String> arrayList) throws FIPAException {
        DFService.deregister(this);
        this.mySkills = arrayList;
        //Register in DF
        DFInteraction.registerInDF(this, Constants.DF_SERVICE_TYPE_SKILL, mySkills);
    }
}
