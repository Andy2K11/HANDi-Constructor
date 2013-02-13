/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import static mscproject.graph.GraphController.*;

/**
 *
 * @author Andy
 */
public class TabDiagram extends Pane {
    private Tab tab = new Tab();
    private ScrollPane scrollPane = new ScrollPane();
    //private Pane pane = new Pane();
    private static int numOfDiagrams = 0;
    
    public TabDiagram() {
        this.setPrefSize(800, 600);
        this.getStyleClass().add("pane");
        scrollPane.setContent(this);
        tab.setContent(scrollPane);
        numOfDiagrams++;
        tab.setText("diagram_"+numOfDiagrams);
        
        this.setOnMouseClicked(handleMouseClicked);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDropped(handleDragDropped);
    }
    
    public Tab getTab() {
        return this.tab;
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
