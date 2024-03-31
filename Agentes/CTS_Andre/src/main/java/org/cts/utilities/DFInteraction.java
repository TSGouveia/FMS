package org.cts.utilities;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;

public class DFInteraction {

    public static boolean registerInDF(Agent agent, String myServiceType, ArrayList<String> myServiceName) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agent.getAID());
        for (int i = 0; i < myServiceName.size(); i++) {
            ServiceDescription sd = new ServiceDescription();
            sd.setType(myServiceType);
            sd.setName(myServiceName.get(i));
            dfd.addServices(sd);
        }
        if(myServiceType.matches(Constants.DF_SERVICE_TYPE_SKILL)){
            ServiceDescription sd = new ServiceDescription();
            sd.setType(Constants.DF_SERVICE_TYPE_RESOURCE);
            sd.setName(agent.getLocalName());
            dfd.addServices(sd);
        }
        try { DFService.register(agent, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean registerInDF(Agent agent, String myServiceType, String myServiceName) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(myServiceType);
        sd.setName(myServiceName);
        dfd.addServices(sd);
        try { DFService.register(agent, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
            return false;
        }
        return true;
    }

    public static DFAgentDescription[] searchInDFByName(Agent agent, String serviceName) throws FIPAException {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setName(serviceName);
        dfd.addServices(sd);
        return DFService.search(agent, dfd);
    }

    public static DFAgentDescription[] searchInDFByType(Agent agent, String serviceType) throws FIPAException {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(serviceType);
        dfd.addServices(sd);
        return DFService.search(agent, dfd);
    }

}
