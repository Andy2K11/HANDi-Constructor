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

import java.net.URL;
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

/**
 *
 * @author Andy
 */
public class UIController implements Initializable {
    
    @FXML private static MenuBar menu;
    @FXML private static MenuBarController menuController;
    @FXML private static TabPane diagramtabs;
    
    public static boolean delete = false;
    public static KeyCode keyCode = KeyCode.UNDEFINED;
    public static final int NONE = 0;
    public static final int DELETE = 3;
        
    public static KeyCode getKeyCode() {
        return keyCode;
    }

    /**
     * Allows the name of the diagram to be changed when the tab header is double clicked.
     * 
     * @param event 
     */
    @FXML
    private void handleMouseClicked(MouseEvent event) {
        if (event.getClickCount() > 1) {
            diagramtabs.getSelectionModel().getSelectedItem().getGraphic().setDisable(false);
        }
        event.consume();
    }
    
    @FXML
    private void handleDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.getTransferMode();
        //diagramtabs.getSelectionModel().selectNext();
        int t = (int) event.getX()/10;
        if (event.getGestureSource() instanceof Tab) {
            diagramtabs.getSelectionModel().select(t);
        }
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuController.setDiagramTabs(diagramtabs);
    }

    public static void setCursor(Cursor cursor) {
        diagramtabs.setCursor(cursor);
    }
}
