package mscproject.graph.controller;

import java.util.TreeSet;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.AbstractLink;
import mscproject.graph.AddLink;
import mscproject.graph.EqualLink;
import mscproject.graph.Graph;
import mscproject.graph.MultiLink;
import mscproject.graph.DropLink;
import mscproject.graph.SimpleLink;
import mscproject.graph.SimpleNode;
import mscproject.ui.MScProjectViewController;
import mscproject.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class NodeController {
    
    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof SimpleNode) {
                SimpleNode sn = (SimpleNode) source;
                switch(MScProjectViewController.getKeyCode()) {
                    case UNDEFINED: sn.incrementComplex();
                        break;
                    case DELETE: 
                        for (AbstractLink link : sn.getLinkList()) {
                            link.getLinkedNode(sn).removeLink(link);
                            ((Graph)sn.getParent()).getChildren().remove(link);
                        }
                        //remove node from diagram
                        ((Graph)sn.getParent()).getChildren().remove(sn);
                        break;
                    case D: TreeSet<Node> subTree = sn.getSubTree();
                        System.out.println(subTree.toString());
                        break;
                }
                
            }
            event.consume();
        }
    };
    
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
                        default: link = new SimpleLink(sns, snt).add();
                            break;
                    }
                    ((Graph)snt.getParent()).getChildren().add(link);
                    link.toBack();
                }    
            }
            event.consume();
        }
    };
}
