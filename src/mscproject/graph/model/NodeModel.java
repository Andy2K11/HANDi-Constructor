/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mscproject.graph.view.NodeView;

/**
 *
 * @author Andy
 */
public class NodeModel implements Serializable {
    
    private String id;
    transient private NodeView view;  // the representation of this node
    private List<LinkModel> links = new ArrayList();
    
    private static int numNodes = 0;
    
    public NodeModel(NodeView view, String id) {
        this.id = id;
        this.view = view;
        numNodes++;
    }
    
    public NodeModel(NodeView view) {
        this(view, "Node_"+(numNodes+1));
    }
    
/*************************** Methods ********************************/
    
    /**
     * When a node is removed all its links must also be delete (or they will
     * have a 'dead end'.
     * The method calls deleteAllLinks() and then calls its own view delete method
     */
    public void delete() {
        deleteAllLinks();
        getView().remove();
    }
    
    /**
     * Connects a link to this node. The same method should be called for 
     * both nodes connected by a link.
     * 
     * @param link 
     */
    public void addLink(LinkModel link) {
        this.links.add(link);
        System.out.println(this.getId()+" adding link " + link.getId());
        for (LinkModel alink: links) {
            System.out.println(this.getId()+" now contains " + alink.getId());
        }
    }
    
    /**
     * Removes a link from this node. The same method should be called for 
     * both nodes connected by a link.
     * 
     * @param link 
     */    
    public void removeLink(LinkModel link) {
        // and finally delete the reference to the deleted link from this node
        System.out.println(this.getId() + " removing link");
        links.remove(link);
    }
    
    /**
     * Removes all links connected to this node, and updates link lists in 
     * previously connected nodes.
     * Use prior to deleting node (removing view).
     */
    public void deleteAllLinks() {
        // first make a copy so that we can remove links from the list without
        // causeing concurrent modification exceptions as link.delete() will 
        // remove links from this node's link list
        //List<LinkModel> temp = new ArrayList(links.size());
        //Collections.copy(temp, links);
        for (LinkModel alink: links) {
            System.out.println(this.getId()+" contains " + alink.getId());
        }
        System.out.println("Deleting all links in "+this.getId()+"There are "+links.size());
        for (LinkModel link: links) {
            // find other node and delete shared link 
            //link.delete();    //throws concurrent modification exception!
            System.out.println("Deleting "+link.getId());
            link.getLinkedNode(this).removeLink(link);

            link.getView().remove();
        }
        // delete all the link references from this node
        //links.clear();
    }
    
/********************* Getters and Setters *************************************/
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NodeView getView() {
        return view;
    }

    public void setView(NodeView view) {
        this.view = view;
    }

    public List<LinkModel> getLinks() {
        return links;
    }

    private void setLinks(List<LinkModel> links) {
        this.links = links;
    }

    public static int getNumNodes() {
        return numNodes;
    }

    public static void setNumNodes(int numNodes) {
        NodeModel.numNodes = numNodes;
    }
    

    
    
}
