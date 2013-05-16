package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.AbstractLink;
import mscproject.graph.Graph;
import mscproject.graph.DropLink;
import mscproject.graph.Shapeable;
import mscproject.graph.SimpleNode;
import mscproject.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class GraphController {

/*****************************MOUSE EVENTS***************************************/    
    
    /**
     * Gives focus to any graph object that the mouse is over, this allows key 
     * events to be directed to the object and not to the ui.
     */
    public static EventHandler handleMouseEntered = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Object source = event.getSource();
            if (source instanceof Node) {
                Node sourceNode = (Node) source;
                sourceNode.requestFocus();
                sourceNode.setEffect(new DropShadow());
            }    
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
        }
    };
    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof Graph) {
                Graph graph = (Graph) source;
                switch (ToolBarController.getSelectedTool()) {
                    case create: 
                        switch(ToolBarController.getNodeType()) {
                            case 0: SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                                graph.getChildren().add(sn);
                                break;
                        }
                        break;
                    case select: //System.out.println("Select");
                        break;
                }
            }
            System.out.println("Mouse clicked: Graph Handler");
            event.consume();
        }
    };
    
/********************************DRAG EVENTS**************************************/    
    
    public static EventHandler handleDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double oldx = 0;
            if (event.getSource() instanceof Shapeable) {
                oldx = ((Shapeable)event.getSource()).getUserX();
            }
            ClipboardContent cbc = new ClipboardContent();
            cbc.putString(String.valueOf(event.getX()-oldx));
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
            double originx = Double.valueOf(event.getDragboard().getString()).doubleValue();
            Node source = (Node) event.getSource();
            Node sourceGesture = (Node) event.getGestureSource();
            Node target = (Node) event.getGestureTarget();
            if (source instanceof Graph) {
                event.acceptTransferModes(TransferMode.ANY);
                if ((sourceGesture instanceof AbstractLink) && (sourceGesture instanceof Shapeable)) {  
                    ((Shapeable)sourceGesture).setControlPoint(x-originx);
                    ((AbstractLink)sourceGesture).updateLayout();
                    System.out.println("Reshaping link path");
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
                    event.getTransferMode();
                    switch (ToolBarController.getSelectedTool()) {
                        case movetree: moveTree(sourceNode, x, y);
                            break;
                        case moveone:
                            sourceNode.setLayoutX(x);
                            sourceNode.setLayoutY(y);
                            for (AbstractLink sl: sourceNode.getLinkList()) {
                                if (sl instanceof Shapeable) {
                                    ((Shapeable)sl).shapeLink();
                                }
                                sl.updateLayout();
                            }
                            break;
                        case copy:
                            SimpleNode sn = new SimpleNode(event.getX(), event.getY());
                            targetTab.getChildren().add(sn);
                            break;
                    }
                } else if (source instanceof AbstractLink) {
                    event.getTransferMode();
                    AbstractLink link = (AbstractLink) source;
                    link.updateLayout();
                }
            }
        }
    };
    
/********************************HELPERS**************************************/   
    
    private static void moveTree(SimpleNode sourceNode, double x, double y) {
        double rootNodeX = sourceNode.getLayoutX();
        double rootNodeY = sourceNode.getLayoutY();
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
    }
    
}
