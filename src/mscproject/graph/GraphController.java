/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import static java.lang.System.*;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import mscproject.ui.MScProjectViewController;

/**
 *
 * @author Andy
 */
public class GraphController {
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            Node target = (Node) event.getTarget();
            //out.println("Source " + source.toString());
            //out.println("Target " + target.toString());
            if (source instanceof SimpleNode) {
                SimpleNode sn = (SimpleNode) source;
                Graph td = ((Graph)sn.getParent());
                if (MScProjectViewController.delete) {
                    PopupControl dialog = new PopupControl();
                    //Popup dialog = new Popup();
                    //dialog.getContent().add(new Circle(0, 0, 50));
                    Button button = new Button("Accept");
                    Skin buttonSkin = button.getSkin();
                    dialog.setSkin(buttonSkin);
                    dialog.setAutoFix(true);
                    dialog.setAutoHide(false);
                    dialog.show(td , 50, 50);  
                    dialog.centerOnScreen();
                    // remove all links connected to this node
                    for (SimpleLink sl : sn.getLinkList()) {
                        sl.getLinkedNode(sn).removeLink(sl);
                        ((Graph)sn.getParent()).getChildren().remove(sl);
                    }
                    //remove node from diagram
                    ((Graph)sn.getParent()).getChildren().remove(sn);    
                } else {
                    sn.incrementComplex();
                }
            } else if (source instanceof Graph) {
                Graph graph = (Graph) source;
                switch(MScProjectViewController.getNodeType()) {
                    case 0: SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                        graph.getChildren().add(sn);
                        break;
                }
            } else if (source instanceof SimpleLink) {
                SimpleLink sl = (SimpleLink) source;
                if (MScProjectViewController.delete) {
                    sl.getNode1().removeLink(sl);
                    sl.getNode2().removeLink(sl);
                    ((Graph)sl.getParent()).getChildren().remove(sl);
                }
                
                if (event.isControlDown()) {
                    sl.toggleSelected();
                } else {
                    sl.toggleNegated();
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
                if (sourceGesture instanceof SimpleLink) {  
                    SimpleLink sl = (SimpleLink) sourceGesture;
                    sl.setControlPoint(x);
                    sl.updateLayout();
                    out.println("Reshaping link path");
                } else {
                    event.consume();
                }
            } else if (source instanceof SimpleNode) {
                event.acceptTransferModes(TransferMode.LINK);
                event.consume();
            } else if (sourceGesture instanceof SimpleLink) {
                event.acceptTransferModes(TransferMode.ANY);    
                SimpleLink sl = (SimpleLink) sourceGesture;
                sl.setControlPoint(x);
                sl.updateLayout();
                out.println("Moving Link");
            }
        }
    };
    
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
                    if (event.getTransferMode()==TransferMode.MOVE) {
                        sourceNode.setLayoutX(x);
                        sourceNode.setLayoutY(y);
                        for (SimpleLink sl: sourceNode.getLinkList()) {
                            sl.updateLayout();
                        }
                    } else if (event.getTransferMode()==TransferMode.COPY) {
                        SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                        targetTab.getChildren().add(sn);
                    }
                } else if (source instanceof SimpleLink) {
                    SimpleLink sl = (SimpleLink) source;
                    //sl.setControlPoint(x, y);
                    sl.updateLayout();
                } else {
                    SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                    targetTab.getChildren().add(sn);
                }
            } else if (target instanceof SimpleNode) {
                if (event.getTransferMode()==TransferMode.LINK) {
                    SimpleNode sns = (SimpleNode) source;
                    SimpleNode snt = (SimpleNode) target;
                    SimpleLink sl;
                    switch(MScProjectViewController.getLinkType()) {
                        case 0: sl = new EqualLink(sns, snt).add();
                            break;
                        case 1: sl = new AddLink(sns, snt).add();
                            break;
                        case 2: sl = new MultiLink(sns, snt).add();
                            break;
                        default: sl = new SimpleLink(sns, snt).add();
                            break;
                    }
                    ((Graph)snt.getParent()).getChildren().add(sl);
                    sl.toBack();
                }
            }
        }
    };
}
