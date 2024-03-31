package org.cts.modules.deployment.server;

import com.google.gson.Gson;
import org.cts.modules.deployment.DeploymentAgent;
import org.cts.utilities.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @PostMapping("/launchProduct")
    public ResponseEntity launchProduct(@RequestBody String receivedContent) {
        Gson gson = new Gson();
        ProductToLaunch myProd = gson.fromJson(receivedContent,ProductToLaunch.class);
        DeploymentAgent.deployment.receivedNewProduct(myProd.getListOfSkills(),myProd.Priority,"A");
        System.err.println("DEBUG: DUARTE MANDOU PRIORIY: " + myProd.Priority);
        System.out.println(receivedContent); //DEBUG RAUL
        return ResponseEntity.ok().build();
    }

    @PostMapping("/plugOperator")
    public ResponseEntity plugOperator(@RequestBody String receivedContent) {
        Gson gson = new Gson();
        ResourceDescription myOperator = gson.fromJson(receivedContent, ResourceDescription.class);
        System.out.println(receivedContent);
        DeploymentAgent.deployment.plugOperator(myOperator.getResourceID(),myOperator.getLocation());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unplugOperator")
    public ResponseEntity unplugOperator(@RequestBody String receivedContent) {
        Gson gson = new Gson();
        ResourceDescription myOperator = gson.fromJson(receivedContent, ResourceDescription.class);
        System.out.println(receivedContent);
        DeploymentAgent.deployment.unplugOperator(myOperator.getResourceID(),myOperator.getLocation());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateConfigurations")
    public ResponseEntity updateConfigurations(@RequestBody String receivedContent) {
        System.out.println(receivedContent);//DEBUG RAUL
        DeploymentAgent.deployment.updateConfiguration(receivedContent);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateTabletIP")
    public ResponseEntity updateTabletIP(@RequestBody String receivedContent) {
        System.out.println(receivedContent);//DEBUG RAUL
        Constants.tabletIP = receivedContent;
        return ResponseEntity.ok().build();
    }

}
