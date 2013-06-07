/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.node;

import development.factory.NetworkFactory;

/**
 *
 * @author Andy Keavey
 */
public class NodeController extends AbstractNodeController {
    
    public NodeController(NetworkFactory factory) {
        super(factory);
        this.model = new NodeModel();
        this.view = new NodeView(this);
    }

}
