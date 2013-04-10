/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import static mscproject.graph.GraphController.*;
/**
 *
 * @author Andy
 */
public class SimpleNode extends Parent {
    static final double radius = 7.0;
    private double x, y;
    private final Circle node = new Circle(0, 0, radius);
    //private Path complex;
    ///private MoveTo mt;
    //private ArcTo at;
    private Arc arc;
    private int iAngle = 0;
    
    private List<SimpleLink> linkList = new ArrayList();
    private List<SimpleNode> linkedNodesList = new ArrayList();
    
    public SimpleNode(double x, double y) {
        this.x = x;
        this.y = y;
        this.getChildren().add(node);
        setLayoutX(x);
        setLayoutY(y);
        //mt = new MoveTo(x, y-radius);
        //at = new ArcTo(90);
        arc = new Arc(0, -(2*radius), radius, radius, -90, iAngle);
        //arc.setFill(null);
        this.getChildren().add(arc);
        
        setOnMouseClicked(handleMouseClicked);
        setOnDragDetected(handleDragDetected);
        setOnDragOver(handleDragOver);
    }
    
    public void incrementComplex() {
        if (iAngle >= 270) {
            iAngle = 0;
        } else {
            iAngle += 90;
        }
        arc.setLength(iAngle);
    }
    
    public void addLink(SimpleLink link) {
        linkList.add(link);
    }
    
    public void removeLink(SimpleLink link) {
        linkList.remove(link);
    }
    
    public void connect(SimpleNode sn) {
        linkedNodesList.add(sn);
    }
    
    public List<SimpleLink> getLinkList() {
        return linkList;
    }
    
    double getX() {
        return x;
    }
    
    double getY() {
        return y;
    }
}
