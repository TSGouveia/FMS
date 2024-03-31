package org.cts.modules.deployment.server;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateConfiguration {

    HashMap<String,ArrayList> Resources = new HashMap<>();

    public HashMap<String, ArrayList> getResources() {
        return Resources;
    }

    public void setResources(HashMap<String, ArrayList> resources) {
        Resources = resources;
    }

    public static void main(String[] args) {
        UpdateConfiguration myConf = new UpdateConfiguration();
        ArrayList<String> mySkills = new ArrayList<>();
        mySkills.add("Skill_Red_1");
        mySkills.add("Skill_Blue_2");
        myConf.Resources.put("Operator1", mySkills);
        ArrayList<String> mySkills2 = new ArrayList<>();
        mySkills2.add("Skill_Blue_2");
        mySkills2.add("Skill_Green_1");
        myConf.Resources.put("Robot1", mySkills2);
        Gson gson = new Gson();
        gson.toJson(myConf);
        System.out.println(gson.toJson(myConf));
    }
}
