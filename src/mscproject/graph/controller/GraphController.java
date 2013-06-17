package mscproject.graph.controller;

import development.factory.HandiNetworkFactory;
import development.factory.NetworkFactory;
import development.mvc.network.connection.AbstractConnectionView;
import development.mvc.network.node.AbstractNodeView;
import development.mvc.network.node.NodeView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.Graph;
import mscproject.ui.ToolBarController;

import mscproject.graph.factory.NodeFactory;
import mscproject.graph.view.Routable;
import static mscproject.ui.ToolBarController.Tool.moveone;
import static mscproject.ui.ToolBarController.Tool.movetree;

/**
 *
 * @author Andy
 */
public class GraphController {
    
    private static NetworkFactory factory = new HandiNetworkFactory();;
    
    public GraphController(Graph graph) {
        //factory = new HandiNetworkFactory();
    }
    
    
    static NodeFactory nodeFactory = new NodeFactory();
    //private Pane graph;

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
                            case 0: 
                                development.mvc.network.node.AbstractNodeController controller = factory.createNode();
                                development.mvc.AbstractView view = controller.getView();        
                                controller.getModel().setX(event.getX());
                                controller.getModel().setY(event.getY());
                                //view.setLayoutX(event.getX());
                                //view.setLayoutY(event.getY());
                                graph.getChildren().add(view);
                                break;
                                // This is the new MVC approach using the Factory Pattern
                            //case 1: NodeView node = nodeFactory.makeNode(NodeFactory.NodeType.MVC, event.getX(), event.getY()).getModel().getView();
                                //graph.getChildren().add(node);
                                //break;                                // This is the new MVC approach using the Factory Pattern
                            //case 1: NodeView node = nodeFactory.makeNode(NodeFactory.NodeType.MVC, event.getX(), event.getY()).getModel().getView();
                                //graph.getChildren().add(node);
                                //break;
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
/***** To Do ************/            
            if (event.getSource() instanceof Routable) {
                //oldx = ((Routable)event.getSource());
            }
            ClipboardContent cbc = new ClipboardContent();
            cbc.putString(String.valueOf(event.getX()-oldx));
            Dragboard db;
            Node source = (Node) event.getSource();
            if (source instanceof NodeView) {
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
            //double originx = Double.valueOf(event.getDragboard().getString()).doubleValue();
            Node source = (Node) event.getSource();
            Node sourceGesture = (Node) event.getGestureSource();
            Node target = (Node) event.getGestureTarget();
            if (source instanceof Graph) {
                event.acceptTransferModes(TransferMode.ANY);
                if (sourceGesture instanceof AbstractConnectionView) {  
                    //AbstractConnectionView conn = (AbstractConnectionView) sourceGesture;
                    //conn.getPath().incrementControlX(x);
                    //System.out.println("Reshaping link path");
                } else {
                    switch (ToolBarController.getSelectedTool()) {
                        case movetree: //moveTree(sourceNode, x, y);
                            break;
                        case moveone:
                            //((AbstractNodeView)sourceGesture).getController().getModel().setX(event.getX());
                            //((AbstractNodeView)sourceGesture).getController().getModel().setY(event.getY());
                            break;
                    }
                    event.consume();
                }
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
                if (source instanceof NodeView) {
                    NodeView sourceNode = (NodeView) source;
                    event.getTransferMode();
                    switch (ToolBarController.getSelectedTool()) {
                        case movetree: //moveTree(sourceNode, x, y);
                            break;
                        case moveone:
                            break;
                        //case copy:
                            //NodeView sn = nodeFactory.makeNode(NodeFactory.NodeType.MVC, x, y).getModel().getView();
                            //targetTab.getChildren().add(sn);
                            //break;
                    }
                } 
            }
        }
    };
    
/********************************HELPERS**************************************/   
    
    /*
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
    }*/
    
}
