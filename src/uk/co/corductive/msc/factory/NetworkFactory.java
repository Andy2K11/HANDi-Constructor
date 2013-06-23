/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.factory;

import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.Operator.Operation;


/**
 *
 * @author Andy
 */
public interface NetworkFactory {
        
    public AbstractNodeController createNode();
    public AbstractConnectionController createConnection(AbstractNodeController node1, AbstractNodeController node2, Operation operation);
    
}
