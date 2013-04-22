/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.ui;

import diagram.DiagramModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
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
public class MScProjectViewController implements Initializable {
    
    @FXML private Pane diagrampane;
    @FXML private TabPane diagramtabs;
    private DiagramModel selectedDiagram;
    @FXML private Label label;
    @FXML private ToggleButton node1button;
    public static boolean delete = false;
    @FXML private static ToggleGroup nodeselect;
    @FXML private static ToggleGroup linkselect;
    @FXML private RadioButton link1, link2, link3;
    private static int linkType = 0;
    
    @FXML
    private void handleNewDiagram(ActionEvent event) {
        //DiagramModel diagram = new DiagramModel().createView("Test Diagram");
        //TabDiagram tabDiagram = new TabDiagram();
        //diagramtabs.getTabs().add(tabDiagram.getTab());
        ScrollTab st = new ScrollTab();
        diagramtabs.getTabs().add(st);
    }
    
    @FXML
    private void handleSaveDiagram(ActionEvent event) {
        Tab tab = diagramtabs.getSelectionModel().getSelectedItem();
        if (tab instanceof ScrollTab) {
            ScrollTab st = (ScrollTab) tab;
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
            for (int i=0; i<jNodes.length(); i++) {
                 JSONObject jNode = jNodes.getJSONObject(i);
                 JSONArray links = jNode.optJSONArray("Link");
                 if (!(links==null)) {
                    for (int j=0; j<links.length(); j++) {
                        JSONObject jLink = links.getJSONObject(j);
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
                     JSONObject jLink = jNode.optJSONObject("Link");
                     String node1String = jLink.getString("Node1");
                     String node2String = jLink.getString("Node2");
                 }
            }
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
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();
        System.out.println("You clicked " + source.toString());
    }
    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case N: node1button.setSelected(!node1button.isSelected());
                break;
            case DELETE: delete = true;
                break;
        }
     
    }
    
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        switch(event.getCode()) {
            case DELETE: delete = false;
                break;
        }
    }
    
    @FXML
    private void handleDragDetected(MouseEvent event) {
        Node source = ((Node)event.getSource());
        Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);
        event.consume();
    }
    
    @FXML
    private void handleDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleNewDiagram(null);

    }
    
    public static int getNodeType() {
        return nodeselect.getToggles().indexOf(nodeselect.getSelectedToggle());
    }
    public static int getLinkType() {
        return linkselect.getToggles().indexOf(linkselect.getSelectedToggle());
    }
}
