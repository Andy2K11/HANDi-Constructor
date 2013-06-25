package mscproject.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import static mscproject.graph.controller.GraphController.*;
import org.json.*;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import uk.co.corductive.msc.ui.MScProject;

/**
 *
 * @author Andy
 */
public class Graph extends Pane {
    private StringProperty name = new SimpleStringProperty();
    //private List<NodeModel> nodes = new ArrayList();
    //private List<LinkModel> links = new ArrayList();

    public Graph(String name) {
        setName(name);
        this.setPrefSize(2384, 1684);
        this.getStyleClass().add("graph");
        this.getStylesheets().add("resources/graph.css");
        this.setOnMouseClicked(handleMouseClicked);
        this.setOnDragOver(handleDragOver);
        this.setOnDragDropped(handleDragDropped);
    }
    
    public final String getName() {
        return name.get();
    }
    
    public final void setName(String name) {
        this.name.set(name);
    }
    
    public final StringProperty nameProperty() {
        return name;
    }
    
    public boolean saveGraph() {
        JSONObject jGraph = new JSONObject();
        jGraph.put("title", getName());
        List<Node> list = this.getChildren();
        for (Node n: list) {
            if (n instanceof AbstractNodeView) {
                jGraph.append("nodes", ((AbstractNodeView)n).getController().getModel().getJSONObject());
            } else if (n instanceof AbstractConnectionView) {
                jGraph.append("connections", ((AbstractConnectionView)n).getController().getModel().getJSONObject());
            }    
        }
        try {
            File saveFile = new File(MScProject.getGlobalUser() + "-" + getName() + ".HANDi");
            System.err.println(saveFile.exists());
            if (!saveFile.exists()) {
                saveFile.createNewFile();

                FileWriter fw = new FileWriter(saveFile);
                //graph.write(fw);
                fw.write(jGraph.toString(4));
                fw.flush();
                fw.close();
                return true;
            }
            return false;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
    
    public JSONObject getJSONObject() {
        JSONObject jGraph = new JSONObject();
        for(Node node: this.getChildren()) {
            
        }
        return jGraph;
    }
}
