package uk.co.corductive.msc.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import mscproject.graph.Graph;
import mscproject.graph.ScrollTab;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import uk.co.corductive.msc.factory.HandiNetworkFactory;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.mvc.AbstractView;
import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.node.AbstractNodeView;

/**
 *
 * @author Andy
 */
public class MenuBarController implements Initializable {

    private TabPane diagramtabs;
    private Tab activeTab;
    
    void setDiagramTabs(final TabPane pane) {
        diagramtabs = pane;
    }
    
    @FXML
    private void handleNewDiagram(ActionEvent event) {
        try {
            ScrollTab st = new ScrollTab();
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
            diagramtabs.setCursor(Cursor.HAND);
            st.getGraph().saveGraph();
        }    
    }
    
    @FXML
    private void handleLoadDiagram(ActionEvent event)  {
        AbstractNodeController nodeController;
        AbstractConnectionController connController;
        
        NetworkFactory factory = new HandiNetworkFactory();
        
        //file select stuff here
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File loadFile = fc.showOpenDialog(null);
        
        ScrollTab st = new ScrollTab("Loading...");
        diagramtabs.getTabs().add(st);
        diagramtabs.getSelectionModel().select(st);
        //File loadFile = new File("diagram_1.HANDi");
        
        try {
            FileReader fr = new FileReader(loadFile);
            JSONTokener jToken = new JSONTokener(fr);
            JSONObject jObj = new JSONObject(jToken);
            String title = jObj.getString("title");
            st.setName(title);
            /* add nodes */
            JSONArray jNodes = jObj.optJSONArray("nodes");
            if (jNodes!=null) {
                for (int i=0; i<jNodes.length(); i++) {
                    AbstractView view;
                    JSONObject node = jNodes.getJSONObject(i);
                    String value = node.getString("value");
                    nodeController = factory.createNode();
                    view = nodeController.getView();        
                    nodeController.getModel().setX(node.getDouble("x"));
                    nodeController.getModel().setY(node.getDouble("y"));
                    nodeController.getModel().setValue(value);
                    nodeController.getModel().setName(node.getString("name"));

                    st.getGraph().getChildren().add(view);
                }
            }
            
            /* add connections */
            JSONArray jLinks = jObj.optJSONArray("connections");
            if (jLinks!=null) {
                for (int i=0; i<jLinks.length(); i++) {
                    JSONObject link = jLinks.getJSONObject(i);
                    AbstractNodeController cont1 = null, cont2 = null;
                    String name1 = link.getString("node1");
                    String name2 = link.getString("node2");
                    List<Node> nodes = st.getGraph().getChildren();
                    for (Node n: nodes) {
                        if (n instanceof AbstractNodeView) {
                            if (((AbstractNodeView)n).getController().getModel().getName().equals(name1)) {
                                cont1 = ((AbstractNodeView)n).getController();
                            } else if (((AbstractNodeView)n).getController().getModel().getName().equals(name2)) {
                                cont2 = ((AbstractNodeView)n).getController();
                            }  
                        }
                    }
                    Operation op = AbstractConnectionModel.stringOperation(link.getString("operator"));
                    if (cont1==null || cont2 ==null) {
                        System.err.println("Null node controller");
                    } else {
                        connController = factory.createConnection(cont1, cont2, op);
                        factory.initConnection(connController, st.getGraph());
                    }
                }
                st.setText(title);
                //System.err.println(jObj.toString(4));
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        
        
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void handleDesign(ActionEvent event) {
        Locale.setDefault(Locale.ENGLISH);
    }
    
    @FXML
    private void handleExperiment(ActionEvent event) {
        Locale.setDefault(new Locale("en", "GB", "Test"));
    }
    
    @FXML
    private void handleUniversity(ActionEvent event) {
        System.out.append("www.sussex.ac.uk");
    }
    
    private ResourceBundle bundle;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = rb;
    }
    
    class NullDiagramException extends Exception {
        
    }
}
