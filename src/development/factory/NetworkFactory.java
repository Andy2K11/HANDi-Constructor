/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.factory;

import development.mvc.network.node.AbstractNodeController;
import development.mvc.network.connection.AbstractConnectionController;
import development.mvc.network.connection.Operator.Operation;


/**
 *
 * @author Andy
 */
public interface NetworkFactory {
        
    public AbstractNodeController createNode();
    public AbstractConnectionController createConnection(AbstractNodeController node1, AbstractNodeController node2, Operation operation);
    
}
