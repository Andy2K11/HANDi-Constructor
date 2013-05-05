/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.TreeSet;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import static mscproject.graph.controller.GraphController.*;

/**
 *
 * @author Andy
 */
public class SimpleLink extends Parent {
    SimpleNode node1, node2;
    double n1x, n1y, n2x, n2y, cx, cy, mx, my;
    final double controlRatioY = 4.0;
    MoveTo mt, nmt;
    LineTo lt, nlt;
    Path path = new Path();
    Path negate = new Path();
    Circle control;
    boolean negated = false;
    
    public SimpleLink(SimpleNode n1, SimpleNode n2) {
        node1 = n1;
        node2 = n2;
        doLayout();
        double controlYFactor = 2 * Math.pow(1.01, Math.abs(n2x - n1x));
        if (n1y < n2y) {
            cx = n2x;
            cy = n1y + (n2y - n1y)/controlYFactor;
        } else {
            cx = n1x;
            cy = n2y + (n1y - n2y)/controlYFactor;
        }
        //cy = (n1y + n2y) / 2;   // y = y1 + (y2 - y1)/2
        //cx = cx + ((Math.random()-0.5)*200);
        mt = new MoveTo(n1x, n1y);
        lt = new LineTo(n2x, n2y);

        path.getElements().setAll(mt, lt);
        path.getStyleClass().add("link");
        control = new Circle(cx, cy, 3);
        control.setVisible(false);
        
        nmt = new MoveTo(mx -5, my);
        nlt = new LineTo(mx +5, my);
        negate.getElements().setAll(nmt, nlt);
        negate.setVisible(negated);
        
        this.getChildren().addAll(path, control, negate);
        
        this.setOnMouseClicked(mscproject.graph.controller.LinkController.handleMouseClicked);
        //this.setOnMouseDragged(n2);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDetected(handleDragDetected);
    }
    
    final void doLayout() {
        n1x = node1.getLayoutX();
        n1y = node1.getLayoutY();
        n2x = node2.getLayoutX();
        n2y = node2.getLayoutY();
        mx = n1x + (n2x - n1x)/2;
        my = n1y + (n2y - n1y)/2;
    }
    
    public void updateLayout() {
        doLayout();
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(n2x);
        lt.setY(n2y);
        control.setCenterX(cx);
        control.setCenterY(cy);
    }
    
    public SimpleLink add() {
        node1.addLink(this);
        node2.addLink(this);
        return this;
    }
    
    public SimpleNode getNode1() {
        return node1;
    }
    
    public SimpleNode getNode2() {
        return node2;
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
    public SimpleNode getLinkedNode(SimpleNode sn) {
        if (sn.equals(node1)) {
            return node2;
        } else if (sn.equals(node2)) {
            return node1;
        } else {
            System.err.println("Neither node in link.");
            return null;
        }
    }
    
    /**
     * Determines if the node supplied as a parameter is a child node. Will 
     * return false if the node is either parent or not connected.
     * 
     * @param sn
     * @return 
     */
    public boolean isSubNode(SimpleNode sn) {
        return (sn.getY() > this.getLinkedNode(sn).getY());
    }
    
    public void setControlPoint(double x) {
        cx = x;
    }
    
    public void setControlPoint(double x, double y) {
        cx = x;
        cy = y;
    }
     
    public void toggleSelected() {
        control.setVisible(!control.isVisible());
    }
    
    public void toggleNegated() {
        negated = !negated;
        negate.setVisible(negated);
    }
    
   
}
