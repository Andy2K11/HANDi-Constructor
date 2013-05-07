/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.AbstractLink;
import mscproject.graph.EqualLink;
import mscproject.graph.Graph;
import mscproject.graph.DropLink;
import mscproject.graph.SimpleNode;
import mscproject.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class GraphController {


    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof Graph) {
                Graph graph = (Graph) source;
                switch(ToolBarController.getNodeType()) {
                    case 0: SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                        graph.getChildren().add(sn);
                        break;
                }
            }
            event.consume();
        }
    };
    
    public static EventHandler handleDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ClipboardContent cbc = new ClipboardContent();
            cbc.putString("");
            Dragboard db;
            Node source = (Node) event.getSource();
            if (source instanceof SimpleNode) {
                if (event.isControlDown()) {
                    db = source.startDragAndDrop(TransferMode.COPY);
                } else if (event.isShiftDown()) {
                    db = source.startDragAndDrop(TransferMode.MOVE);
                } else {
                    db = source.startDragAndDrop(TransferMode.LINK);
                }
            } else {
                db = source.startDragAndDrop(TransferMode.COPY_OR_MOVE);
            }
            db.setContent(cbc);
        }
    };
    
    public static EventHandler handleDragOver = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            double x = event.getX();
            double y = event.getY();
            Node source = (Node) event.getSource();
            Node sourceGesture = (Node) event.getGestureSource();
            Node target = (Node) event.getGestureTarget();
            if (source instanceof Graph) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                if (sourceGesture instanceof DropLink) {  
                    DropLink sl = (DropLink) sourceGesture;
                    //sl.setControlPoint(x);
                    sl.updateLayout();
                    //out.println("Reshaping link path");
                } else {
                    event.consume();
                }
            } else if (source instanceof SimpleNode) {
                event.acceptTransferModes(TransferMode.ANY);    //Changed from link temp
                event.consume();
            } else if (sourceGesture instanceof DropLink) {
                event.acceptTransferModes(TransferMode.ANY);    
                DropLink sl = (DropLink) sourceGesture;
                //sl.setControlPoint(x);
                sl.updateLayout();
                //out.println("Moving Link");
            }
        }
    };
    
    
    /**
     * Handles both moving and copying of nodes, and the reshaping of the link path?
     */
    public static EventHandler handleDragDropped = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            double x = event.getX();
            double y = event.getY();
            Object source = event.getGestureSource();
            Object target = event.getGestureTarget();
            //out.println("Drag dropped " + target.toString());
            if (target instanceof Graph) {
                Graph targetTab = (Graph) target;
                if (source instanceof SimpleNode) {
                    SimpleNode sourceNode = (SimpleNode) source;
                    if (true) {
                        event.getTransferMode();
                        double rootNodeX = sourceNode.getLayoutX();
                        double rootNodeY = sourceNode.getLayoutY();
                        //sourceNode.setLayoutX(x);
                        //sourceNode.setLayoutY(y);
                        for (Node node: sourceNode.getSubTree()) {
                            if (node instanceof SimpleNode) {
                                SimpleNode sn = (SimpleNode) node;
                                double diffX = sn.getLayoutX() - rootNodeX;
                                double diffY = sn.getLayoutY() - rootNodeY;
                                sn.setLayoutX(x+diffX);
                                sn.setLayoutY(y+diffY);
                                for (AbstractLink link: sn.getLinkList()) {
                                        link.updateLayout();   
                                }
                            }
                        }
                    } else if (event.getTransferMode()==TransferMode.MOVE) {
                        sourceNode.setLayoutX(x);
                        sourceNode.setLayoutY(y);
                        for (AbstractLink sl: sourceNode.getLinkList()) {
                            sl.updateLayout();
                        }
                    } else if (event.getTransferMode()==TransferMode.COPY) {
                        SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                        targetTab.getChildren().add(sn);
                    }
                } else if (source instanceof DropLink) {
                    DropLink sl = (DropLink) source;
                    //sl.setControlPoint(x, y);
                    sl.updateLayout();
                } else {
                    SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                    targetTab.getChildren().add(sn);
                }
            } 
        }
    };
}
