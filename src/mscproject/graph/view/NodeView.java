/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 *
 * @author Andy
 */
public class NodeView extends Parent {
    
    private Circle node;
    //private double X, Y;
    private final double RADIUS = 7.0;
    
    /**
     * This node view is set to the position x, y.
     * As the circle which represents the centre of the node is at 0, 0 it
     * will appear at the position x, y exactly.
     * It is important not to confuse the position of the NodeView(x, y) with
     * the positions of it's elements.
     * 
     * @param x
     * @param y 
     */
    public NodeView(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        node = new Circle(0, 0, RADIUS);        // circle is added to top left of this NodeView extending Parent
        this.getChildren().add(node);
        node.getStyleClass().add("node");
    }
    
    public void remove() {
        ((Pane)this.getParent()).getChildren().remove(this);
    }
    
}
