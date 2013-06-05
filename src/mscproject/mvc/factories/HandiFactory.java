/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc.factories;

import mscproject.mvc.AbstractNodeModel;
import mscproject.mvc.GenericConnectionController;
import mscproject.mvc.GenericNodeController;
import mscproject.mvc.HandiConnectionModel;
import mscproject.mvc.HandiNodeModel;
import mscproject.mvc.InterfaceController;
import mscproject.mvc.InterfaceOperator;
import mscproject.mvc.factories.NetworkFactory;

/**
 *
 * @author Andy
 */
public class HandiFactory implements NetworkFactory {
    
    private InterfaceController nodeController, connectionController;
    
    @Override
    public InterfaceController createNode() {
        nodeController = new GenericNodeController(new HandiNodeModel());
        return nodeController;
    }

    @Override
    public InterfaceController createConnection(AbstractNodeModel node1, AbstractNodeModel node2, InterfaceOperator.Operation operation) {
        HandiConnectionModel model = new HandiConnectionModel(node1, node2, operation);
        connectionController = new GenericConnectionController(model);
        return connectionController;
    }
}
