/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.factory;

import development.mvc.network.connection.AbstractConnectionController;
import development.mvc.network.connection.ConnectionController;
import development.mvc.network.connection.Operator.Operation;
import development.mvc.network.node.AbstractNodeController;
import development.mvc.network.node.NodeController;

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
