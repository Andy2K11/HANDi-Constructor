/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.model;

import java.io.Serializable;
import mscproject.graph.view.AbstractLinkView;
import org.json.JSONObject;


/**
 *
 * @author Andy
 */
public class LinkModel implements Serializable {
    
    public enum LinkType{EQUALS, ADDITION, MULTIPLICATION, SIMPLE} 
    private String id;
    private NodeModel node1;
    private NodeModel node2;
    private LinkType linkType;
    transient private AbstractLinkView view;
    
    public LinkModel(NodeModel node1, NodeModel node2, LinkType linkType, AbstractLinkView view) {
        id = node1.getId() + "-" + node2.getId();
        this.node1 = node1;
        this.node2 = node2;
        this.linkType = linkType;
        this.view = view;
        System.out.println(node1.getId());
        System.out.println(node2.getId());
    }
    
    /**
     * We can't add this to the linked nodes until this has been instantiated,
     * therefore the add method will normal be called immediately after a call to 
     * the constructor. A reference to this is returned to allow it to be used
     * like a builder. e.g. new LinkModel(node1, node2).add();
     * 
     * @return 
     */
    public LinkModel add() {
        getNode1().addLink(this);
        getNode2().addLink(this);
        return this;
    }
    
    /**
     * When this link is removed it must delete its model element from each connected
     * node and also delete its view from the scene graph.
     */
    public void delete() {
        getNode1().removeLink(this);
        getNode2().removeLink(this);
        getView().remove();
    }

         /**
     * Method to fetch the node connected to the other end of this link, the 
     * calling node supplies itself as a parameter. If the supplied node does
     * not match either node connected to this link then a null value is returned.
     * This method is required to iterate through a network.
     * 
     * @param aNode
     * @return 
     */   
    public NodeModel getLinkedNode(NodeModel aNode) {
        if (aNode == node1) {
            return node2;
        } else if (aNode == node2) {
            return node1;
        } else {
            System.err.println("Neither node in link.");
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public JSONObject getJNode() {
        JSONObject jNode = new JSONObject();
        jNode.put("id", this.id);
        jNode.put("node1", this.getNode1().getId());
        jNode.put("node2", this.getNode2().getId());
        //jNode.put("view", this.getView().getJNode());
        return jNode;
    }    
    /************** Getters and Setters ************************/
    
    public NodeModel getNode1() {
        return node1;
    }

    public void setNode1(NodeModel node1) {
        this.node1 = node1;
    }

    public NodeModel getNode2() {
        return node2;
    }

    public void setNode2(NodeModel node2) {
        this.node2 = node2;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public AbstractLinkView getView() {
        return view;
    }

    public void setView(AbstractLinkView linkView) {
        this.view = linkView;
    }
    
    
    
}
