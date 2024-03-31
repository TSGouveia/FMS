package org.cts.modules.product;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.ArrayList;

public class ProdutAgent extends Agent {

    protected AID resourceExecuter;
    protected ArrayList<String> myProcess;
    protected String myPriority;
    protected String myLocation;
    protected String nextLocation;

    @Override
    protected void setup() {
        // Get Arguments
        //1 - List of Skills - ArrayList with Strings
        //2 - Priority - String (Robot, Human)
        //3 - Location - String
        myProcess = (ArrayList<String>) this.getArguments()[0];
        myPriority = (String) this.getArguments()[1];
        myLocation = (String) this.getArguments()[2];

        //Get Next Skill behaviour
        this.addBehaviour(new NextSkillBehaviour());

    }

}
