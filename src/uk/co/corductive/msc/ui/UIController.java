/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.ui;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import mscproject.graph.ScrollTab;

/**
 *
 * @author Andy
 */
public class UIController implements Initializable {
    
    @FXML private static MenuBar menu;
    @FXML private static MenuBarController menuController;
    @FXML private static TabPane diagramtabs;
    @FXML private FlowPane toolbar;
    @FXML private static Label status;
    
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
    private void handleMouseClicked(MouseEvent event) {
        if (event.getClickCount() > 1) {
            diagramtabs.getSelectionModel().getSelectedItem().getGraphic().setDisable(false);
        }
        event.consume();
    }
    
    /*
     * Most key events will need to be sent to other handlers when the focus
     * could be on any part of the ui.
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        //MScProjectViewController.keyCode = event.getCode();
        System.err.println("Handle key: " + this.getClass().getName());
        //toolbar.getOnKeyPressed().handle(event);
        event.consume();
    }    
    
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        UIController.keyCode = KeyCode.UNDEFINED;
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
        diagramtabs.getTabs().add(new ScrollTab());
       
        /*menu.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuController.setDiagramTabs(diagramtabs);
                System.err.println("Filter set");
            }
        });*/
        
    }
    
    private static LinkedList<String> msgHistory = new LinkedList();
    public static void setStatus(String msg) {
        msgHistory.addFirst(msg);
        if (msgHistory.size()>3) {
            msgHistory.removeLast();
        }
        StringBuilder sb = new StringBuilder();
        for (String s: msgHistory) {
            sb.append(s);
            sb.append("\n");
        }
        status.setText(sb.toString());
    }

    public static void setCursor(Cursor cursor) {
        diagramtabs.setCursor(cursor);
    }
}
