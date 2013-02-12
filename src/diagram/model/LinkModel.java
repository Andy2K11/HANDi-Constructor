/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram.model;

import diagram.view.LinkView;

/**
 *
 * @author Andy
 */
public class LinkModel {
    private NodeModel node1, node2;
    private boolean isNode1Parent = true;
    private LinkView linkView;

    public LinkModel(NodeModel node1, NodeModel node2) {
        this.node1 = node1;
        this.node2 = node2;   
    }
    
    public LinkModel createView(LinkModel linkModel) {
        linkView = new LinkView(node1.getNodeView().getLayoutX(), node1.getNodeView().getLayoutY(),
                node2.getNodeView().getLayoutX(), node2.getNodeView().getLayoutY()).createController();
        return this;
    }
    
    public LinkView getLinkView() {
        return linkView;
    }
}
