package org.cts.modules.deployment;

import org.cts.modules.deployment.server.UpdateConfiguration;

import java.util.ArrayList;

public interface fromServerToAgent {
    void receivedNewProduct(ArrayList<String> myProcess, String myPriority, String myLocation);

    void plugOperator(String operatorID, String location);
    void unplugOperator(String operatorID, String location);

    void updateProductPosition(String position);

    void updateConfiguration(String myConf);
}
