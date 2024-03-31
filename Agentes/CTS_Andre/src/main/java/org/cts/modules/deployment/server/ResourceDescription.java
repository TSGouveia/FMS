package org.cts.modules.deployment.server;

import com.google.gson.Gson;

public class ResourceDescription {

    String ResourceID, Location;

    public String getResourceID() {
        return ResourceID;
    }

    public void setResourceID(String resourceID) {
        ResourceID = resourceID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public static void main(String[] args) {
        ResourceDescription myDesc = new ResourceDescription();
        myDesc.setLocation("B");
        myDesc.setResourceID("Operator1");
        Gson gson = new Gson();
        gson.toJson(myDesc);
        System.out.println(gson.toJson(myDesc));
    }
}
