/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.connection;

import development.mvc.network.node.AbstractNodeController;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionController extends AbstractConnectionController {
    
    private AbstractConnectionViewFactory factory = new ConnectionViewFactory();
    
    public ConnectionController(AbstractNodeController node1, AbstractNodeController node2) {
        super(node1, node2);
        this.model = new ConnectionModel(node1.getModel(), node2.getModel());
        this.view = factory.createConnecionView(this);
        this.view = new ConnectionView(this);
    }

}
