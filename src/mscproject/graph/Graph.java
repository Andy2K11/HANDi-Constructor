package mscproject.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import static mscproject.graph.controller.GraphController.*;
import org.json.*;

/**
 *
 * @author Andy
 */
public class Graph extends Pane {
    private String id;
    //private List<NodeModel> nodes = new ArrayList();
    //private List<LinkModel> links = new ArrayList();

    public Graph(String id) {
        this.id = id;
        this.setPrefSize(800, 600);
        this.getStyleClass().add("graph");
        
        this.setOnMouseClicked(handleMouseClicked);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDropped(handleDragDropped);
    }
    
    public JSONObject getJSONObject() {
        JSONObject jGraph = new JSONObject();
        for(Node node: this.getChildren()) {
            
        }
        return jGraph;
    }
}
