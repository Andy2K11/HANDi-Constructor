/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import static mscproject.graph.controller.GraphController.*;
/**
 *
 * @author Andy
 */
public class SimpleNode extends Parent implements Comparable<SimpleNode> {
    static final double radius = 7.0;
    private double x, y;
    private final Circle node = new Circle(0, 0, radius);
    //private Path complex;
    ///private MoveTo mt;
    //private ArcTo at;
    private Arc arc;
    private int iAngle = 0;
    
    private List<AbstractLink> linkList = new ArrayList();
    //private List<SimpleNode> linkedNodesList = new ArrayList();
    private static int numNodes = 0;
    private String name;
    
    public SimpleNode(double x, double y, String name) {
        numNodes++;
        this.x = x;
        this.y = y;
        this.name = name;
        this.getChildren().add(node);
        setLayoutX(x);
        setLayoutY(y);
        //mt = new MoveTo(x, y-radius);
        //at = new ArcTo(90);
        arc = new Arc(0, -(2*radius), radius, radius, -90, iAngle);
        //arc.setFill(null);
        this.getChildren().add(arc);
        
        
        setOnMouseEntered(handleMouseEntered);
        setOnMouseExited(handleMouseExited);
        setOnMouseClicked(handleMouseClicked);
        
        setOnDragDetected(handleDragDetected);
        setOnDragOver(handleDragOver);
        //setOnDragDropped(mscproject.graph.controller.NodeController.handleDragDropped);
        
        // set style for each component, or have a master stylesheet for quick skinability?
        this.getStylesheets().add("resources/nodeStyle.css");
        arc.getStyleClass().add("complex");
    }
    
    public SimpleNode(double x, double y) {
        this(x, y, "node"+numNodes);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return this.getName();
    }
    
    /**
     * Implements comparable using the height of the node within the tree.
     * As y axis values start from zero at the top of the screen, a lesser y 
     * axis value will return a negative integer indicating that the node is 
     * higher in the hierarchy (comes first in the tree), and vice versa.
     * "thisObject less than anotherObject return negative" 
     * 
     * @param sn
     * @return 
     */
    @Override
    public int compareTo(SimpleNode sn) {
        if (this.getLayoutY() < sn.getLayoutY()) {
            return -1;
        } else if (this.getLayoutY() > sn.getLayoutY()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public void incrementComplex() {
        if (iAngle >= 270) {
            iAngle = 0;
        } else {
            iAngle += 90;
        }
        arc.setLength(iAngle);
    }
    
    void addLink(AbstractLink link) {
        linkList.add(link);
    }
    
    /**
     * Removal of a link should always come from the link as it has to notify 
     * the connected nodes at both ends.
     * 
     * @param link 
     */
    void removeLink(AbstractLink link) {
        linkList.remove(link);
    }
    
    /*
    public void connect(SimpleNode sn) {
        linkedNodesList.add(sn);
    }*/
    
    public List<AbstractLink> getLinkList() {
        return linkList;
    }
    
    /*
    double getX() {
        return x;
    }
    
    double getY() {
        return y;
    }*/
    
    public String getName() {
        return name;
    }
    
    void setName(String name) {
        this.name = name;
    }

    /* A TreeSet is used here because it is ordered, sortable, and only allows
     * unique entries (it's a set!). We want to store the network in decending
     * order but not have duplicates where many links connect the same node.
     */
    private TreeSet<Node> subTree = new TreeSet<Node>();
    
    /**
     * Method to create an hierarchically ordered subtree from this node.
     * 
     * @return All nodes in the sub tree from this node, including this node.
     */
    public TreeSet<Node> getSubTree() {
        subTree.clear();
        subTree.add(this);
        
        /* 
         * For all links, if link connects a sub node, add it to the tree and
         * check if it has any further sub nodes.
         * We don't want to include any nodes connected via a equallink as these
         * are defined to be at the same level (root).
         */
        for (AbstractLink link: this.getLinkList()) {
            SimpleNode linkedNode = link.getLinkedNode(this);
            if (link.isSubNode(linkedNode) && !(link instanceof EqualLink)) {
                //subTree.add(linkedNode);
                subTree.addAll(linkedNode.getSubTree());
            }
        }
        return subTree;
    }
    
    public void deleteNode() {
        this.removeEventHandler(MouseEvent.MOUSE_EXITED, mscproject.graph.controller.GraphController.handleMouseExited);
        for (AbstractLink link : this.getLinkList()) {
            link.getLinkedNode(this).removeLink(link);
            ((Graph)this.getParent()).getChildren().remove(link);
        }
        //remove node from diagram
        ((Graph)this.getParent()).getChildren().remove(this);
        
    }
    
}
