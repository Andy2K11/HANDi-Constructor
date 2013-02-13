/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class SimpleLink extends Path {
    SimpleNode node1, node2;
    double n1x, n1y, n2x, n2y, cx, cy;
    MoveTo mt;
    LineTo lt;
    
    public SimpleLink(SimpleNode n1, SimpleNode n2) {
        node1 = n1;
        node2 = n2;
        doLayout();
        mt = new MoveTo(n1x, n1y);
        lt = new LineTo(n2x, n2y);  
        this.getElements().setAll(mt, lt);
    }
    
    final void doLayout() {
        n1x = node1.getLayoutX();
        n1y = node1.getLayoutY();
        n2x = node2.getLayoutX();
        n2y = node2.getLayoutY();
        if (n1y < n2y) {
            cx = n2x;
            cy = n1y;
        } else {
            cx = n1x;
            cy = n2y;
        }
        //updateLayout();
    }
    
    void updateLayout() {
        doLayout();
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(n2x);
        lt.setY(n2y);
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
}
