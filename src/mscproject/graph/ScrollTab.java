/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

/**
 *
 * @author Andy
 */
public class ScrollTab extends Tab {
    private ScrollPane scrollPane;
    private Graph graph;
    private static int tabNum = 0;
    
    
    public ScrollTab(String name) {
        super(name);
        tabNum++;
        scrollPane = new ScrollPane();
        graph = new Graph(name);
        scrollPane.setContent(graph);
        this.setContent(scrollPane);
    }
    
    public ScrollTab() {
        this("diagram_" + (tabNum+1));
    }

    
    public Graph getGraph() {
        return graph;
    }
}
