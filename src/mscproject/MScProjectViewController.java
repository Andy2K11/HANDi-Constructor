/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject;

import diagram.DiagramModel;
import diagram.model.NodeModel;
import diagram.view.NodeView;
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
    
    @FXML
    private void handleNewDiagram(ActionEvent event) {
        DiagramModel diagram = new DiagramModel().createView("Test Diagram");
        diagramtabs.getTabs().add(diagram.getDiagramView());
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
    /*
    @FXML
    private void handleDragDropped(DragEvent event) {
        double x = event.getX();
        double y = event.getY();
        selectedDiagram = (DiagramModel) diagramtabs.getSelectionModel().getSelectedItem();
        Object source = event.getGestureSource();
        if (source instanceof ToggleButton) {
            if (((ToggleButton) source).getId().equals("node1button")) {
                
                NodeModel nm = new NodeModel().add(selectedDiagram).createView(x, y, selectedDiagram);
            } else {
                System.out.println(((ToggleButton) source).getId());
            }
        } else if (source instanceof NodeView) {
            if (event.getTransferMode()==TransferMode.COPY) {
                NodeModel nm = new NodeModel().createView(x, y, selectedDiagram);
            } else if (event.getTransferMode()==TransferMode.MOVE) {
                ((NodeView) source).setLayoutX(x);
                ((NodeView) source).setLayoutY(y);
            }
            
        }
        event.setDropCompleted(true);
        event.consume();
    }*/
    
    /*
    @FXML
    private void handleMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        selectedDiagram = (DiagramModel) diagramtabs.getSelectionModel().getSelectedItem();
        System.out.println("Mouse Clicked: " + x + ", " + y);
        if (node1button.isSelected()) {
            NodeModel nm = new NodeModel().createView(x, y, selectedDiagram);  
        }
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleNewDiagram(null);
    }    
}
