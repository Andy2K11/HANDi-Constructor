package mscproject.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import static mscproject.graph.GraphController.*;
import org.json.*;

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
    
    /*
    public Graph(File loadFile) {
        try {
            FileReader fr = new FileReader(loadFile);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }*/
        
    private List<SimpleNode> getNodeList() {
        List<SimpleNode> nodeList = new ArrayList();
        for (Object o: this.getChildren()) {
            if (o instanceof SimpleNode) {
                nodeList.add((SimpleNode) o);
            }
        }
        return nodeList;
    }
    
    private List<SimpleLink> getLinkList() {
        List<SimpleLink> linkList = new ArrayList();
        for (Object o: this.getChildren()) {
            if (o instanceof SimpleLink) {
                linkList.add((SimpleLink) o);
            }
        }
        return linkList;
    }
    
    public void saveDiagram() {
        
        JSONObject graph = new JSONObject();
        graph.put("Title", this.id);
        
        JSONObject jNode;
        for (SimpleNode sn: getNodeList()) {
            jNode = new JSONObject();
            jNode.put("Name", sn.getName());
            jNode.put("X", sn.getX());
            jNode.put("Y", sn.getY());
       
            List<SimpleLink> links = sn.getLinkList();
            for (SimpleLink sl: links) {
                JSONObject jLink = new JSONObject();
                jLink.accumulate("Node1", sl.getNode1().getName());
                jLink.accumulate("Node2", sl.getNode2().getName());
                String linkClass = sl.getClass().toString();
                linkClass = linkClass.substring(linkClass.lastIndexOf('.')+1);
                jLink.accumulate("Type", linkClass);
                jNode.accumulate("Link", jLink);
            }
            
            graph.accumulate("Nodes", jNode);
        }
        
        // Save json obects to file.
        try {
            File saveFile = new File(this.id+".HANDi");
            System.err.println(saveFile.exists());
            saveFile.createNewFile();
            
            FileWriter fw = new FileWriter(saveFile);
            //graph.write(fw);
            fw.write(graph.toString(4));
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
