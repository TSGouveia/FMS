package org.cts.modules.deployment.server;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ProductToLaunch {

    ArrayList<String> ListOfSkills = new ArrayList<>();
    String Priority;

    public ArrayList<String> getListOfSkills() {
        return ListOfSkills;
    }

    public void setListOfSkills(ArrayList<String> listOfSkills) {
        this.ListOfSkills = listOfSkills;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        this.Priority = priority;
    }

    public static void main(String[] args) {
        ProductToLaunch myProd = new ProductToLaunch();
        myProd.ListOfSkills.add("SkillX");
        myProd.ListOfSkills.add("SkillY");
        myProd.setPriority("Robot");
        Gson gson = new Gson();
        gson.toJson(myProd);
        System.out.println(gson.toJson(myProd));
    }
}
