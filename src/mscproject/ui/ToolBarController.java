/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Andy
 */
public class ToolBarController implements Initializable {
    
    @FXML private ToggleButton node1button;
    @FXML private static ToggleGroup linkselect, nodeselect;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();
        System.out.println("You clicked " + source.toString());
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
    
    void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case N: node1button.setSelected(!node1button.isSelected());
                break;
        }
    }
    
    public static int getNodeType() {
        return nodeselect.getToggles().indexOf(nodeselect.getSelectedToggle());
    }
    
    public static int getLinkType() {
        return linkselect.getToggles().indexOf(linkselect.getSelectedToggle());
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
