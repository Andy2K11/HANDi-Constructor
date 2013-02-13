/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.collections.ObservableList;
import javafx.scene.Node;
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
}
