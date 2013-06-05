/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Andy
 */
public abstract class AbstractController implements InterfaceController {
    private AbstractModel model;
    private AbstractView view;
    
    public AbstractController(AbstractModel model) {
        // do nothing
    }
    
    @Override 
    public EventHandler<MouseEvent> getOnMouseEnteredHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource();
                if (source instanceof Node) {
                    Node sourceNode = (Node) source;
                    sourceNode.requestFocus();
                    sourceNode.setEffect(new DropShadow());
                }
                event.consume();
            }
        };
    }            
                
    @Override
    public EventHandler getOnMouseExitedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object source = event.getSource();
                if (source instanceof Node) {
                    Node sourceNode = (Node) source;
                    try {
                        sourceNode.getParent().requestFocus();
                    } catch (NullPointerException ex) {
                        System.err.println("Could not remove focus from node.\n  Node may have been deleted.");
                    }
                    sourceNode.setEffect(null);
                }
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler getOnDragOverHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.MOVE);
                ((Node)event.getSource()).setEffect(new DropShadow());
                event.consume();
            }
        };
    }
}
