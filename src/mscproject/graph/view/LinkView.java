/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class LinkView extends Parent {
    private double n1x, n1y, n2x, n2y;
    private Path path = new Path();
    private MoveTo mt = new MoveTo();
    private LineTo lt = new LineTo(); 
    
    public LinkView() {
        path.getElements().addAll(mt, lt);
        this.getChildren().add(path);
        path.getStyleClass().add("link");
    }
    
    public void setPosition(double x1, double y1, double x2, double y2) {
        n1x = x1;
        n1y = y1;
        n2x = x2;
        n2y = y2;
        mt.setX(x1);
        mt.setY(y1);
        lt.setX(x2);
        lt.setY(y2);
    }
    
    public void remove() {
        ((Pane)this.getParent()).getChildren().remove(this);
    }    
}
