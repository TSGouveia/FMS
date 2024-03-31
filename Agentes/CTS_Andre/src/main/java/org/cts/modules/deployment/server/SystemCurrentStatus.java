package org.cts.modules.deployment.server;

import com.google.gson.Gson;

import java.util.HashMap;

public class SystemCurrentStatus {
    HashMap<String, String> Resources = new HashMap<>();
    String ProductLocation = "A";

    public String getProductLocation() {
        return ProductLocation;
    }

    public void setProductLocation(String productLocation) {
        ProductLocation = productLocation;
    }

    public void plugResource(String operatorID, String location) {
        Resources.put(operatorID, location);
    }

    public void unplugResource(String operatorID) {
        Resources.remove(operatorID);
    }

    public static void main(String[] args) {
        SystemCurrentStatus myDesc = new SystemCurrentStatus();
        myDesc.Resources.put("Operator1", "B");
        myDesc.Resources.put("Robot1", "F");
        Gson gson = new Gson();
        gson.toJson(myDesc);
        System.out.println(gson.toJson(myDesc));
    }

    public HashMap<String, String> getPluggedResources() {
        return Resources;
    }
}
