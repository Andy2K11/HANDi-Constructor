package mscproject.graph.controller;

import java.util.TreeSet;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.AbstractLink;
import mscproject.graph.AddLink;
import mscproject.graph.DropLink;
import mscproject.graph.EqualLink;
import mscproject.graph.Graph;
import mscproject.graph.MultiLink;
import mscproject.graph.SimpleNode;
import mscproject.graph.SingleLink;
import mscproject.ui.MScProjectViewController;
import mscproject.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class NodeController {
    
/********************************MOUSE EVENTS*************************************/
    
    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof SimpleNode) {
                SimpleNode sn = (SimpleNode) source;
                switch(ToolBarController.getSelectedTool()) {
                    case delete: sn.deleteNode();
                        break;
                    case create: break;
                    case select: //check for key events
                        switch(MScProjectViewController.getKeyCode()) {
                            case UNDEFINED: sn.incrementComplex();
                                break;
                            case DELETE:  

                                break;
                            case D: TreeSet<Node> subTree = sn.getSubTree();
                                System.out.println(subTree.toString());
                                break;
                        }
                        break;
                }   
            }
            System.out.println("Mouse clicked: Node Handler");
            event.consume();
        }
    };
 
/********************************DRAG EVENTS*************************************/    
    
    /**
     * A drag drop gesture from another SimpleNode indicates a link to be formed.
     * Check that the TransferMode is link and thus not another object that has 
     * been moved over a node.
     */
    public static EventHandler handleDragDropped =  new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            double x = event.getX();
            double y = event.getY();
            Object source = event.getGestureSource();
            Object target = event.getGestureTarget();
            
            if (target instanceof SimpleNode) {
                if (event.getTransferMode()==TransferMode.LINK) {
                    SimpleNode sns = (SimpleNode) source;
                    SimpleNode snt = (SimpleNode) target;
                    AbstractLink link;
                    switch(ToolBarController.getLinkType()) {
                        case 0: link = new EqualLink(sns, snt).add();
                            break;
                        case 1: link = new AddLink(sns, snt).add();
                            break;
                        case 2: link = new MultiLink(sns, snt).add();
                            break;
                        case 3: link = new DropLink(sns, snt).add();
                            break;
                        default: link = new SingleLink(sns, snt).add();
                            break;
                    }
                    ((Graph)snt.getParent()).getChildren().add(link);
                    link.toBack();
                }    
            }
            event.consume();
        }
    };
    
/****************************KEY EVENTS*******************************************/
    
    
    
    public static EventHandler handleKeyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            Object source = event.getSource();
            if (source instanceof SimpleNode) {
                SimpleNode sn = (SimpleNode) source;
                switch(event.getCode()) {
                    case DELETE: sn.deleteNode();
                        break;
                }
            }
            event.consume();
        }
    };
}
