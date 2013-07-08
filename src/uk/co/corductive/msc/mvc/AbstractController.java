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
package uk.co.corductive.msc.mvc;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public abstract class AbstractController {
    
    
    protected AbstractController() {
        super();
    }
    
    public abstract AbstractModel getModel();
    public abstract AbstractView getView();
    
    public abstract EventHandler<MouseEvent> getOnMouseClickedHandler();
    public abstract EventHandler<MouseEvent> getOnMouseDraggedHandler();
    public abstract EventHandler<MouseEvent> getOnDragDetectedHandler();
    public abstract EventHandler<DragEvent> getOnDragDroppedHandler();
    public abstract EventHandler<KeyEvent> getOnKeyPressedHandler(); 
    
    public EventHandler<MouseEvent> getOnMouseEnteredHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource();
                if (source instanceof Node) {
                    Node sourceNode = (Node) source;
                    //sourceNode.requestFocus();
                    //sourceNode.setEffect(new DropShadow());
                }
                event.consume();
            }
        };
    }            
                
    public EventHandler<MouseEvent> getOnMouseExitedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource();
                if (source instanceof Node) {
                    Node sourceNode = (Node) source;
                    try {
                        //sourceNode.getParent().requestFocus();
                    } catch (NullPointerException ex) {
                        System.err.println("Could not remove focus from node.\n  Node may have been deleted.");
                    }
                    //sourceNode.setEffect(null);
                }
                event.consume();
            }
        };
    }

    
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                event.consume();
            }
        };
    }
    
    public EventHandler<DragEvent> getOnDragOverHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                //((Node)event.getSource()).setEffect(new DropShadow());
                event.consume();
                //System.out.println("<DragEvent> Drag Over");
            }
        };
    }
}
