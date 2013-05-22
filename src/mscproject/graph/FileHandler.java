/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Andy
 */
public class FileHandler {
/*
 * private List<SimpleNode> getNodeList() {
        List<SimpleNode> nodeList = new ArrayList();
        for (Object o: this.getChildren()) {
            if (o instanceof SimpleNode) {
                nodeList.add((SimpleNode) o);
            }
        }
        return nodeList;
    }
    
    private List<DropLink> getLinkList() {
        List<DropLink> linkList = new ArrayList();
        for (Object o: this.getChildren()) {
            if (o instanceof DropLink) {
                linkList.add((DropLink) o);
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
            jNode.put("X", sn.getLayoutX());
            jNode.put("Y", sn.getLayoutY());
       
            //List<SimpleLink> links = sn.getLinkList();
            
            
            graph.accumulate("Nodes", jNode);
        }
        for (DropLink sl: getLinkList()) {
                JSONObject jLink = new JSONObject();
                jLink.accumulate("Node1", sl.getNode1().getName());
                jLink.accumulate("Node2", sl.getNode2().getName());
                String linkClass = sl.getClass().toString();
                linkClass = linkClass.substring(linkClass.lastIndexOf('.')+1);
                jLink.accumulate("Type", linkClass);
                //jNode.accumulate("Link", jLink);
                graph.accumulate("Links", jLink);
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
 */    
}
