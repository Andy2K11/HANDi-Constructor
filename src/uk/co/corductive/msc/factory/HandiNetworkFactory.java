/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.factory;

import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.ConnectionController;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.node.NodeController;

/**
 *
 * @author Andy Keavey
 */
public class HandiNetworkFactory implements NetworkFactory {

    HandiComponentFactory component = new HandiComponentFactory();
    
    public HandiNetworkFactory() {
        
    }
    
    @Override
    public AbstractNodeController createNode() {
        AbstractNodeController controller = new NodeController(this);
        
        return controller;
    }

    @Override
    public AbstractConnectionController createConnection(AbstractNodeController node1, AbstractNodeController node2, Operation operation) {
        AbstractConnectionController controller = new ConnectionController(node1, node2);
        controller.getModel().makeConnection();
        controller.getView().createPath(component.getConnectionPath(operation));
        controller.getView().createNegate(component.getNegationPath(operation));
        return controller;
    }
}
