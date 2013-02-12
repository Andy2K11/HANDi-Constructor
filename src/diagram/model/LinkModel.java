/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram.model;

/**
 *
 * @author Andy
 */
public class LinkModel {
    private NodeModel node1, node2;
    private boolean isNode1Parent = true;

    public LinkModel(NodeModel node1, NodeModel node2) {
        this.node1 = node1;
        this.node2 = node2;
    }
}
