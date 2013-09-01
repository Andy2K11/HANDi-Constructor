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
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

/**
 * Defines essential and common behaviour for the Controller element of an MVC
 * design.
 * 
 * @author Andy Keavey
 */
public abstract class AbstractController {
    
    
    protected AbstractController() {
        super();
    }
    
    /**
     * 
     * 
     * @return the model
     */
    public abstract AbstractModel getModel();
    
    /**
     * 
     * @return the view
     */
    public abstract AbstractView getView();
    
    /**
     * 
     * @return an event handler of type MouseEvent to be used for OnClick events.
     */
    public abstract EventHandler<MouseEvent> getOnMouseClickedHandler();
    
    /**
     * 
     * @return an event handler for mouse drags. 
     */
    public abstract EventHandler<MouseEvent> getOnMouseDraggedHandler();
    
    /**
     * 
     * @return an event handler for mouse drag releases.
     */
    public abstract EventHandler<MouseDragEvent> getOnMouseDragReleased();
    
    /**
     * 
     * 
     * @return an event handler to detect drag events.
     */
    public abstract EventHandler<MouseEvent> getOnDragDetectedHandler();
    
    /**
     * 
     * @return an event handler for when the user drops a dragged object
     */
    public abstract EventHandler<DragEvent> getOnDragDroppedHandler();
    
    /**
     * 
     * @return an event handler to manage user keyboard presses
     */
    public abstract EventHandler<KeyEvent> getOnKeyPressedHandler(); 
    
    /**
     * Currently doesn't do anything.
     * 
     * @return an event handler for when the mouse moves over an object.
     */
    public EventHandler<MouseEvent> getOnMouseEnteredHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource();
                if (source instanceof Node) {
                    Node sourceNode = (Node) source;
                }
                event.consume();
            }
        };
    }            
        
    /**
     * Removes any effects set on an object. This handler is called after an 
     * object is deleted so it may need handle NullPointerExceptions.
     * 
     * @return an event handler for when the mouse leaves an object
     */
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
                    sourceNode.setEffect(null);
                }
                
                event.consume();
            }
        };
    }

    /**
     * Simply consumes the event, may be overridden by subclasses. 
     * 
     * Implies that inheritance hierarchy may need altering.
     * 
     * @return an event handler for mouse presses
     */
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                event.consume();
            }
        };
    }
    
    /**
     * Highlights an object when another object is dragged over it. CSS effects
     * are not applied to Drag-Drop events.
     * 
     * @return a handler for when objects are dragged over in Drag-Drop events
     */
    public EventHandler<DragEvent> getOnDragOverHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                ((Node)event.getSource()).setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLUE, 5, 0.2, 0.0, 0.0));
                event.consume();
                //System.out.println("<DragEvent> Drag Over");
            }
        };
    }
}
