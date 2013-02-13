/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
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
    private List<SimpleLink> linkList = new ArrayList();
    
    public SimpleNode(double x, double y) {
        this.x = x;
        this.y = y;
        this.getChildren().add(node);
        setLayoutX(x);
        setLayoutY(y);
        
        setOnMouseClicked(handleMouseClicked);
        setOnDragDetected(handleDragDetected);
        setOnDragOver(handleDragOver);
    }
    
    public void addLink(SimpleLink link) {
        linkList.add(link);
    }
    
    public List<SimpleLink> getLinkList() {
        return linkList;
    }
}
