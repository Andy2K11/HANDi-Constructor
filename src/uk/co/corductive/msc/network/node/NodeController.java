/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.node;

import uk.co.corductive.msc.factory.NetworkFactory;

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
