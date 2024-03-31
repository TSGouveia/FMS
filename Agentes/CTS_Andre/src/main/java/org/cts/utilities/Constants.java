package org.cts.utilities;

import java.util.ArrayList;

public class Constants {

    //DF_SERVICES
    //Global Deployment
    public static final String DF_SERVICE_TYPE_SKILL = "SKILL_TYPE";
    //Local Deployment
    public static final String DF_SERVICE_TYPE_TRANSPORT = "TRANSPORT_TYPE";
    public static final String DF_SERVICE_NAME_TRANSPORT = "TRANSPORT_NAME";
    public static final String DF_SERVICE_TYPE_RESOURCE = "RESOURCE_TYPE";;

    //Ontologies
    public static final String ONTOLOGY_NEGOTIATE_NEXT_RESOURCE = "CNET_NEXT_RESOURCE";
    public static final String ONTOLOGY_REQUEST_TRANSPORT = "REQ_TRANSPORT";
    public static final String ONTOLOGY_REQUEST_EXECUTE_SKILL = "REQ_EXECUTE_SKILL";
    public static final String ONTOLOGY_REQUEST_UNPLUG_RESOURCE = "REQ_UNPLUG_RESOURCE";
    public static final String ONTOLOGY_REQUEST_UPDATE_CONFIG = "REQ_UPDATE_CONFIG";

    public static String tabletIP = "192.168.2.102";

    public enum ResourceType {
        Robot,Station,Human
    }

    public enum Locations {
        A,B,C,D,E,F
    }

    public enum Skills {
        Skill_Red_1,
        Skill_Red_2,
        Skill_Green_1,
        Skill_Green_2,
        Skill_Blue_1,
        Skill_Blue_2,
        Skill_Yellow_1,
        Skill_Yellow_2
    }

}
