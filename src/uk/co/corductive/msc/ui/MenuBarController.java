/*
 * Copyright (C) 2013 Andy Keavey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.corductive.msc.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import mscproject.graph.AbstractGraphView;
import mscproject.graph.GraphView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import uk.co.corductive.msc.factory.GraphFactory;
import uk.co.corductive.msc.factory.HandiNetworkFactory;
import uk.co.corductive.msc.factory.NetworkFactory;
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
    private GraphFactory factory = new GraphFactory();
    
    void setDiagramTabs(final TabPane pane) {
        diagramtabs = pane;
    }
    
    @FXML
    private void handleNewDiagram(ActionEvent event) {
        ScrollTab tab = factory.createGraph();
        diagramtabs.getTabs().add(tab);
    }
    
    @FXML
    private void handleSaveDiagram(ActionEvent event) {
        activeTab = diagramtabs.getSelectionModel().getSelectedItem();
        if (activeTab instanceof ScrollTab) {
            ScrollTab st = (ScrollTab) activeTab;
            //diagramtabs.setCursor(Cursor.HAND);
            Node node = st.getGraph();
            if (node instanceof GraphView) {
                GraphView graph = (GraphView) node;
                saveGraph(graph);
            }
            
        }    
    }
    
    @FXML
    private void handleLoadDiagram(ActionEvent event)  {
        AbstractNodeController nodeController;
        AbstractConnectionController connController;
        
        NetworkFactory gFactory = new HandiNetworkFactory();
        
        //file select stuff here
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File loadFile = fc.showOpenDialog(null);
        
        /* create a new empty tab - add to tab pane - select new tab */
        ScrollTab tab = factory.createGraph();
        diagramtabs.getTabs().add(tab);
        diagramtabs.getSelectionModel().select(tab);     // switch to newly loaded diagram
        //File loadFile = new File("diagram_1.HANDi");
        
        try {
            FileReader fr = new FileReader(loadFile);
            JSONTokener jToken = new JSONTokener(fr);
            JSONObject jObj = new JSONObject(jToken);
            String title = jObj.getString("title");
           
            tab.setName(title);
            /* add nodes */
            JSONArray jNodes = jObj.optJSONArray("nodes");
            if (jNodes!=null) {
                for (int i=0; i<jNodes.length(); i++) {
                    JSONObject jNode = jNodes.getJSONObject(i);
                    String value = jNode.getString("value");
                    nodeController = gFactory.createNode(jNode.getDouble("x"), jNode.getDouble("y"), tab.getGraph());
                    nodeController.getModel().setValue(value);
                    nodeController.getModel().setName(jNode.getString("name"));
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
                    Node node = tab.getGraph();
                    if (node instanceof AbstractGraphView) {
                        List<Node> nodes = ((AbstractGraphView)node).getChildren();
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
                            connController = gFactory.createConnection(cont1, cont2, op, (Pane)tab.getGraph());
                        }
                    }
                }
                // tab.setText(title);  // title is displayed via text field.
                // System.err.println(jObj.toString(4));
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }  catch (NullPointerException ex) {
            System.err.println("The most likely cause for this exception is the "
                    + "user cancelling the load diagram dialog box");
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
        //this.handleNewDiagram(new ActionEvent());
    }
    
    class NullDiagramException extends Exception {
        
    }
    
    public boolean saveGraph(GraphView view) {
        /* get the JSON model data for the graph as a whole */
        JSONObject jGraph = view.getController().getModel().getJSONObject();
        
        /* now go through all the nodes and connections for that graph and 
         * add their JSON representations */
        List<Node> list = view.getChildren();
        for (Node n: list) {
            if (n instanceof AbstractNodeView) {
                jGraph.append("nodes", ((AbstractNodeView)n).getController().getModel().getJSONObject());
            } else if (n instanceof AbstractConnectionView) {
                jGraph.append("connections", ((AbstractConnectionView)n).getController().getModel().getJSONObject());
            }    
        }
        try {
            File saveFile = new File(MScProject.getGlobalUser() + "-" + view.getController().getModel().getName() + ".HANDi");
            System.err.println("File already exists: " + saveFile.exists());
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            FileWriter fw = new FileWriter(saveFile);
            fw.write(jGraph.toString(4));
            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
}
