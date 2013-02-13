/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import static mscproject.graph.GraphController.*;
/**
 *
 * @author Andy
 */
public class Graph extends Pane {
    private String id;
    
    public Graph(String id) {
        this.id = id;
        this.setPrefSize(800, 600);
        this.getStyleClass().add("pane");
        
        this.setOnMouseClicked(handleMouseClicked);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDropped(handleDragDropped);
    }
    
        
    private List<SimpleNode> getNodeList() {
        List<SimpleNode> nodeList = new ArrayList();
        for (Object o: this.getChildren()) {
            if (o instanceof SimpleNode) {
                nodeList.add((SimpleNode) o);
            }
        }
        return nodeList;
    }
    
    public void saveDiagram() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("\n\r");
        for (SimpleNode sn: getNodeList()) {
            sb.append("XY: ");
            sb.append(sn.getX());
            sb.append(", ");
            sb.append(sn.getY());
            sb.append("\n\r");
        }
        System.err.println(sb);
    }
}
