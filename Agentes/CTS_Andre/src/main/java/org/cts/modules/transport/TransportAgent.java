package org.cts.modules.transport;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.cts.utilities.Constants;
import org.cts.utilities.DFInteraction;

import java.util.ArrayList;

public class TransportAgent extends Agent {

    protected String currentProductLocation = Constants.Locations.A.name();

    @Override
    protected void setup() {
        //Register in DF
        DFInteraction.registerInDF(this, Constants.DF_SERVICE_TYPE_SKILL, Constants.DF_SERVICE_NAME_TRANSPORT);
        //Request Transport
        this.addBehaviour(new REQUESTExecuteTransport(this,
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology(Constants.ONTOLOGY_REQUEST_TRANSPORT))));
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
