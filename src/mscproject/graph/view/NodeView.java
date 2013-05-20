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
    private final double RADIUS = 7.0;
    
    public NodeView() {
        this(0, 0);
    }
    
    public NodeView(double x, double y) {
        node = new Circle(x, y, RADIUS);
        this.getChildren().add(node);
        node.getStyleClass().add("node");
    }
    
    public void remove() {
        ((Pane)this.getParent()).getChildren().remove(this);
    }
}
