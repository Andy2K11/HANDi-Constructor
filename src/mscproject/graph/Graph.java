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
        this.setPrefSize(2384, 1684);
        this.getStyleClass().add("graph");
        
        this.setOnMouseClicked(handleMouseClicked);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDropped(handleDragDropped);
        /*HandiComponentFactory component = new HandiComponentFactory();
        AbstractConnectionPath path = component.getConnectionPath(Operation.ADDITION);
        path.setStartX(20.0);
        path.setStartY(20.0);
        path.setEndX(80.0);
        path.setEndY(30.0);
        this.getChildren().add(path);*/
        
    }
    
    public JSONObject getJSONObject() {
        JSONObject jGraph = new JSONObject();
        for(Node node: this.getChildren()) {
            
        }
        return jGraph;
    }
}
