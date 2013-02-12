/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import diagram.model.NodeModel;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author Andy
 */
public class DiagramView extends Tab {
    private ScrollPane scrollPane = new ScrollPane();
    private Pane pane = new Pane();
    private DiagramController diagramController;
    
    public DiagramView() {
        super();
        pane.setPrefSize(800, 600);
        pane.getStyleClass().add("pane");
        scrollPane.setContent(pane);
        this.setContent(scrollPane);
    }
    
    public DiagramView createController(DiagramModel dm) {
        diagramController = new DiagramController(pane, dm);
        return this;
    }
    public DiagramView(String title) {
        this();
        this.setText(title);
    }
    
    public ObservableList<Node> getChildren() {
        return pane.getChildren();
    }
}
