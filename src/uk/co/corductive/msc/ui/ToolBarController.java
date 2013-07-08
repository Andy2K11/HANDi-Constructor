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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
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
    
    @FXML private ToggleButton create, moveone, movetree, copy, copytree, delete;
    @FXML private ToggleButton node1button;
    @FXML private static ToggleGroup toolselect, linkselect, nodeselect;
    
    public static enum Tool {select, create, moveone, movetree, copy, copytree, delete}
    static Tool tool = Tool.select;
    
    public static Tool getSelectedTool() {
        return tool;
    }
    
    private static void selectTool() {
        Toggle toggle = toolselect.getSelectedToggle();
        if (toggle instanceof ToggleButton) {
            ToggleButton selectedTool = (ToggleButton) toggle;
            String id = selectedTool.getId();
            tool = Tool.valueOf(id);
        } else /* if null */ {
            tool = Tool.select;
        }       
    }
    
    @FXML
    private void handleToolAction(ActionEvent event) {
        selectTool();
        System.out.println(tool.toString() + ": " + tool.ordinal());
        event.consume();
    }
    
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
    
    @FXML
    private void handleMouseEntered(MouseEvent event) {
        Object source = event.getSource();
        if (source instanceof Node) {
            Node sourceNode = (Node) source;
            sourceNode.requestFocus();
        }
    }
    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        //MScProjectViewController.setStatus("Handle key: " + this.getClass().getName());
        switch(event.getCode()) {
            case N: node1button.setSelected(!node1button.isSelected());
                break;
            case INSERT: create.setSelected(!create.isSelected());
                selectTool();
                break;
            case DELETE: delete.setSelected(!delete.isSelected());
                selectTool();
                break;
        }
        //UIController.setStatus("Key: " + event.getCode().getName());
        event.consume();
    }
    
    public static int getNodeType() {
        return nodeselect.getToggles().indexOf(nodeselect.getSelectedToggle());
    }
    
    public static int getLinkType() {
        return linkselect.getToggles().indexOf(linkselect.getSelectedToggle());
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //rb.getClass().getResource("resources/toolbar_expm.properties");
        //rb = ResourceBundle.getBundle("resources/toolbar_expm.properties");
    }
}
