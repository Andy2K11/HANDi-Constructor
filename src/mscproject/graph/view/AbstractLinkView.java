/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class AbstractLinkView extends Parent {
    
    protected Path path = new Path();
    private double startX, startY, endX, endY;
    //protected PathElement start, end;

    
    public AbstractLinkView() {
        //path.getElements().addAll(start, end);
        this.getChildren().add(path);
        path.getStyleClass().add("link");
    }
    
    public abstract void setStartPosition(double x, double y);
    public abstract void setEndPosition(double x, double y);
        
    public void remove() {
        ((Pane)this.getParent()).getChildren().remove(this);
    } 
    
    
/*************************** Getters and Setters ****************************/   

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }    
}
