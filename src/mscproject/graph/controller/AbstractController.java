/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Andy
 */
public class AbstractController {
        public static EventHandler handleMouseEntered = new EventHandler<MouseEvent>() {
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
    
    public static EventHandler handleMouseExited = new EventHandler<MouseEvent>() {
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
    
    /*
    public EventHandler handleDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ClipboardContent cbc = new ClipboardContent();
            // a ha - now using MVC we just pass a reference to the node model.
            cbc.putString("");
            Dragboard db;
            Node source = (Node) event.getSource();
            db = source.startDragAndDrop(TransferMode.LINK);
            db.setContent(cbc);
            event.consume();
        }
    };   */
    
    public static EventHandler handleDragOver = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            event.acceptTransferModes(TransferMode.MOVE);
            ((Node)event.getSource()).setEffect(new DropShadow());
            event.consume();
        }
    };
}
