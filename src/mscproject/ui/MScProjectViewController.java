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
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Andy
 */
public class MScProjectViewController implements Initializable {
    
    @FXML private MenuBar menu;
    @FXML private MenuController menuController;
    @FXML private TabPane diagramtabs;
    
    public static boolean delete = false;
    public static KeyCode keyCode = KeyCode.UNDEFINED;
    public static final int NONE = 0;
    public static final int DELETE = 3;

    @FXML private RadioButton link1, link2, link3;
    private static int linkType = 0;
        
    public static KeyCode getKeyCode() {
        return keyCode;
    }

    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        MScProjectViewController.keyCode = event.getCode();
    }    
    
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        switch(event.getCode()) {
            case DELETE: delete = false;
                break;
        }
        //keyCode = NONE;
    }
    

    
    @FXML
    private void handleDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuController.setDiagramTabs(diagramtabs);
        /*menu.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuController.setDiagramTabs(diagramtabs);
                System.err.println("Filter set");
            }
        });*/
        
    }
    

}
