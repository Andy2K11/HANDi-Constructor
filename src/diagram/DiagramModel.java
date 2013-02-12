package diagram;

import diagram.model.LinkModel;
import diagram.model.NodeModel;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author Andy
 */
public class DiagramModel {
    private static int numOfDiagrams = 0;
    private List<NodeModel> nodes = new LinkedList();
    private List<LinkModel> links = new LinkedList();
    private DiagramView diagramView;
    
    public DiagramModel() {
        numOfDiagrams++;
    }
    
    public DiagramModel createView(String title) {
        diagramView = new DiagramView("diagram_"+numOfDiagrams).createController(this);
        return this;
    }
     
    public void saveDiagram() {
        for (NodeModel node: nodes) {
            // do save
        }
        for (LinkModel link: links) {
            // do save
        }
    }

    public List<NodeModel> getNodes() {
        return nodes;
    }
    
    public DiagramView getDiagramView() {
        return diagramView;
    }
}
