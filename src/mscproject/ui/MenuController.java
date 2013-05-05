package mscproject.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import mscproject.graph.MultiLink;
import mscproject.graph.ScrollTab;
import mscproject.graph.SimpleLink;
import mscproject.graph.SimpleNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Andy
 */
public class MenuController implements Initializable {

    private TabPane diagramtabs;
    private Tab activeTab;
    
    void setDiagramTabs(final TabPane pane) {
        diagramtabs = pane;
    }
    
    @FXML
    private void handleNewDiagram(ActionEvent event) {
        //DiagramModel diagram = new DiagramModel().createView("Test Diagram");
        //TabDiagram tabDiagram = new TabDiagram();
        //diagramtabs.getTabs().add(tabDiagram.getTab());
        ScrollTab st = new ScrollTab();
        try {
            diagramtabs.getTabs().add(st);
        } catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }
    
    @FXML
    private void handleSaveDiagram(ActionEvent event) {
        activeTab = diagramtabs.getSelectionModel().getSelectedItem();
        if (activeTab instanceof ScrollTab) {
            ScrollTab st = (ScrollTab) activeTab;
            st.getGraph().saveDiagram();
        }    
    }
    
    @FXML
    private void handleLoadDiagram(ActionEvent event)  {
        //file select stuff here
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File loadFile = fc.showOpenDialog(null);
        
        ScrollTab st = new ScrollTab("Loaded_Diagram");
        diagramtabs.getTabs().add(st);
        diagramtabs.getSelectionModel().select(st);
        //File loadFile = new File("diagram_1.HANDi");
        try {
            FileReader fr = new FileReader(loadFile);
            JSONTokener jToken = new JSONTokener(fr);
            JSONObject jObj = new JSONObject(jToken);
            String title = jObj.getString("Title");
            JSONArray jNodes = jObj.getJSONArray("Nodes");
            for (int i=0; i<jNodes.length(); i++) {
                JSONObject jNode = jNodes.getJSONObject(i);
                st.getGraph().getChildren().add(new SimpleNode(jNode.optDouble("X"), jNode.optDouble("Y"), jNode.getString("Name")));
            }
            //for (int i=0; i<jNodes.length(); i++) {
                 //JSONObject jNode = jNodes.getJSONObject(i);
                 //JSONArray links = jNode.optJSONArray("Links");
                JSONArray jLinks = jObj.getJSONArray("Links");
                if (!(jLinks==null)) {
                    for (int j=0; j<jLinks.length(); j++) {
                        JSONObject jLink = jLinks.getJSONObject(j);
                        String node1String = jLink.getString("Node1");
                        String node2String = jLink.getString("Node2");
                        //search for node objects
                        SimpleNode node1 = null, node2 = null;
                        for (Node n: st.getGraph().getChildren()) {
                            if (n instanceof SimpleNode) {
                                SimpleNode seekNode = (SimpleNode) n;
                                if (seekNode.getName().equals(node1String)) {
                                    node1 = seekNode;
                                } else if (seekNode.getName().equals(node2String)) {
                                    node2 = seekNode;
                                }
                            }
                        }
                        if (node1!=null && node2!=null) {
                            SimpleLink sl = new MultiLink(node1, node2).add();
                            st.getGraph().getChildren().add(sl);
                            sl.toBack();
                        }
                        String type = jLink.getString("Type");
                        if (type.equals("MultiLink")) {
                        //    st.getGraph().getChildren().add(new MultiLink())
                        }
                    }
                 } else {
                     JSONObject jLink = jObj.optJSONObject("Links");
                     String node1String = jLink.getString("Node1");
                     String node2String = jLink.getString("Node2");
                 }
            //}
            st.setText(title);
            System.err.println(jObj.toString(4));
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        //st.getGraph().getChildren().add(new SimpleNode(300.0, 250.0));
        
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void handleUniversity(ActionEvent event) {
        System.out.append("www.sussex.ac.uk");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    class NullDiagramException extends Exception {
        
    }
}
