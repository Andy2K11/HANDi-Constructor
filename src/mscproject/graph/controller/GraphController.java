package mscproject.graph.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.co.corductive.msc.factory.HandiNetworkFactory;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import uk.co.corductive.msc.network.node.NodeView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import mscproject.graph.Graph;
import uk.co.corductive.msc.ui.ToolBarController;

import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.moveone;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.movetree;

/**
 *
 * @author Andy
 */
public class GraphController {
    
    private static NetworkFactory factory = new HandiNetworkFactory();;
    Graph graph = null;
    
    public GraphController(Graph graph) {
        this.graph = graph;
    }
    

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
                                uk.co.corductive.msc.network.node.AbstractNodeController controller = factory.createNode();
                                uk.co.corductive.msc.mvc.AbstractView view = controller.getView();        
                                controller.getModel().setX(event.getX());
                                controller.getModel().setY(event.getY());
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
                if (source instanceof AbstractNodeView) {   
                    if (event.getTransferMode() == TransferMode.COPY) {
                        uk.co.corductive.msc.network.node.AbstractNodeController controller;
                        uk.co.corductive.msc.mvc.AbstractView view;
                        
                        Dragboard db = event.getDragboard();
                        switch (ToolBarController.getSelectedTool()) {
                            case copy:
                                controller = factory.createNode();
                                view = controller.getView();        
                                controller.getModel().setX(event.getX());
                                controller.getModel().setY(event.getY());
                                controller.getModel().setValue((String) db.getContent(DataFormat.PLAIN_TEXT));
                                targetTab.getChildren().add(view);
                                break;
                            case copytree:
                                JSONObject jObject = (JSONObject) db.getContent(AbstractModel.dataFormat);
                                Map<String, String> nameMap = new HashMap<>();
                                
                                JSONObject root = jObject.getJSONObject("root");
                                double rootX = root.getDouble("x");
                                double rootY = root.getDouble("y");
                                controller = factory.createNode();
                                view = controller.getView();        
                                controller.getModel().setX(event.getX());
                                controller.getModel().setY(event.getY());
                                controller.getModel().setValue(root.getString("value"));
                                //controller.getModel().setName(root.getString("name")+"cpy");
                                nameMap.put(root.getString("name"), controller.getModel().getName());
                                targetTab.getChildren().add(view);
                                
                                JSONArray array = jObject.getJSONArray("nodes");
                                for (int i=0; i<array.length(); i++) {
                                    JSONObject node = array.getJSONObject(i);
                                    controller = factory.createNode();
                                    view = controller.getView();        
                                    controller.getModel().setX(event.getX() + (node.getDouble("x")) - rootX);
                                    controller.getModel().setY(event.getY() + (node.getDouble("y")) - rootY);
                                    controller.getModel().setValue(node.getString("value"));
                                    //controller.getModel().setName(node.getString("name")+"cpy");
                                    nameMap.put(node.getString("name"), controller.getModel().getName());
                                    targetTab.getChildren().add(view);
                                }
                                
                                /* add connections */
                                JSONArray jLinks = jObject.optJSONArray("connections");
                                
                                if (jLinks!=null) {
                                    System.out.println(jLinks.toString(4));
                                    for (int i=0; i<jLinks.length(); i++) {
                                        JSONObject link = jLinks.getJSONObject(i);
                                        AbstractNodeController cont1 = null, cont2 = null;
                                        String name1 = nameMap.get(link.getString("node1"));
                                        String name2 = nameMap.get(link.getString("node2"));
                                        List<Node> nodes = targetTab.getChildren();
                                        for (Node n: nodes) {
                                            if (n instanceof AbstractNodeView) {
                                                if (((AbstractNodeView)n).getController().getModel().getName().equals(name1)) {
                                                    cont1 = ((AbstractNodeView)n).getController();
                                                } else if (((AbstractNodeView)n).getController().getModel().getName().equals(name2)) {
                                                    cont2 = ((AbstractNodeView)n).getController();
                                                }  
                                            }
                                        }
                                        String opString = link.getString("operator");
                                        Operation op = AbstractConnectionModel.stringOperation(opString);
                                        if (cont1==null || cont2 ==null) {
                                            System.err.println("Null node controller");
                                        } else {
                                            AbstractConnectionController connController = factory.createConnection(cont1, cont2, op);
                                            factory.initConnection(connController, (Pane) targetTab);
                                            System.out.println("Copy complete.");
                                        }
                                    }
                                }
                                break;
                        }
                    }
                } 
            }
        }
    };
    
/********************************HELPERS**************************************/   
    
    
}
