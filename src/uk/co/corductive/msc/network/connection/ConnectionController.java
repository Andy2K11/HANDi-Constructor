/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.connection;

import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionController extends AbstractConnectionController {
    
    public ConnectionController(AbstractNodeController node1, AbstractNodeController node2, Operation operator) {
        super(node1, node2);
        this.model = new ConnectionModel(node1.getModel(), node2.getModel(), operator);
        this.view = new ConnectionView(this);
    }

}
