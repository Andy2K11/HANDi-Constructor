/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.ui;

import diagram.DiagramModel;
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
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import mscproject.graph.TabDiagram;

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
        TabDiagram tabDiagram = new TabDiagram();
        diagramtabs.getTabs().add(tabDiagram.getTab());
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
