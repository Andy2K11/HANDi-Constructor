package mscproject.graph.controller;

import java.util.Set;
import java.util.TreeSet;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
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
import mscproject.graph.factory.LinkFactory;
import mscproject.graph.model.LinkModel;
import mscproject.graph.model.LinkModel.LinkType;
import mscproject.graph.model.NodeModel;
import mscproject.graph.view.LinkView;
import mscproject.graph.view.NodeView;
import mscproject.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class NodeController extends AbstractController {
        // Keep a record of all nodes in a set, use for checking uniqness.
    private static Set<NodeModel> nodes = new TreeSet<>();
    
    private static DataFormat dataFormat = new DataFormat("node");
    private NodeModel model;
    private static NodeModel temp;  // dragboard data is copied and will not give reference to actual source object
    //private NodeView view;
    
    private static LinkFactory linkFactory = new LinkFactory();
    
    public NodeController(NodeModel nodeM) {
        this.model = nodeM;
        //nodes.add(this.model);  //needed?
        //this.view = nodeV;
        model.getView().setOnMouseClicked(this.handleMouseClicked);
        model.getView().setOnDragDetected(this.handleDragDetected);
        model.getView().setOnDragOver(handleDragOver);
        model.getView().setOnDragDropped(this.handleDragDropped);
        model.getView().setOnKeyPressed(handleKeyPressed);
        
        model.getView().setOnMouseEntered(handleMouseEntered);
        model.getView().setOnMouseExited(handleMouseExited);
    }

    public NodeModel getModel() {
        return model;
    }

    
/********************************MOUSE EVENTS*************************************/
    
    
    public EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            /*if (source instanceof SimpleNode) {
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
            }*/
            if (source instanceof NodeView) {
                //NodeView nodeView = (NodeView) source;
                switch(ToolBarController.getSelectedTool()) {
                    case delete: getModel().delete();    //remove the node and it's links from model    
                        break;
                    default: 
                        System.out.println(getModel().getId()+ " confirm link size "+getModel().getLinks().size());
                        break;
                }
            }
            System.out.println("Mouse clicked: Node Handler");
            event.consume();
        }
    };
 
/********************************DRAG EVENTS*************************************/    
    
    
    public EventHandler handleDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("Drag detected");
            temp = getModel();
            System.out.println(temp.toString());
            ClipboardContent cbc = new ClipboardContent();
            // a ha - now using MVC we just pass a reference to the node model.
            Test t = new Test();
            System.out.println("Test before: "+t.toString());
            cbc.put(dataFormat, t);
            Dragboard db;
            Node source = (Node) event.getSource();
            db = source.startDragAndDrop(TransferMode.MOVE);
            db.setContent(cbc);
            event.consume();
            
        }
    };   
    
    /**
     * A drag drop gesture from another SimpleNode indicates a link to be formed.
     * Check that the TransferMode is link and thus not another object that has 
     * been moved over a node.
     */
    public EventHandler handleDragDropped =  new EventHandler<DragEvent>() {
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
            } else if (target instanceof NodeView) {
                event.getTransferMode();
                Dragboard db = event.getDragboard();
                Object o = db.getContent(dataFormat);   // Is this actually getting a ref to our node model?
                NodeModel sourceNM;
                //if (o instanceof NodeModel) {
                    NodeView sourceNV = (NodeView) source;
                    //sourceNM = (NodeModel) o;
                    Test t = (Test) o;
                    System.out.println("test after " + t.toString());
                    System.out.println("via static " + temp.toString());
                    /*
                     * Because the view held by a model is made transient it is null after
                     * been placed on the drag board.
                     */
                    //sourceNM.setView(sourceNV);
                    NodeView targetNV = (NodeView) target;
                    // get the selected link type on the toolbar and convert it to a type the link factory understands
                    LinkType linkType = linkTypeAdapter(ToolBarController.getLinkType());
                    // create a new link using the factory and return it's view. 
                    LinkController cont = linkFactory.makeLink(temp, getModel(), linkType);
                    LinkView linkView = cont.getModel().getView();
                    // set the position of the link view to the x y coords of the source and target nodes
                    linkView.setPosition(sourceNV.getLayoutX(), sourceNV.getLayoutY(), targetNV.getLayoutX(), targetNV.getLayoutY());
                    // add the view to the parent pane
                    ((Graph)sourceNV.getParent()).getChildren().add(linkView);
                    linkView.toBack();  // put the links behind nodes so that user can iteract with nodes
                    System.out.println("Confirm 1 "+cont.getModel().getNode1().getLinks().size());
                    System.out.println("Confirm 2 "+cont.getModel().getNode2().getLinks().size());

                //}
            } 
            event.consume();

        }
    };
    
    
    private LinkType linkTypeAdapter(int selectedLink) {
        switch(selectedLink) {
            case 0: return LinkType.EQUALS;
            case 1: return LinkType.ADDITION;
            case 2: return LinkType.MULTIPLICATION;
            case 3: return LinkType.SIMPLE;
            default: return LinkType.SIMPLE;
        }
    }
    
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
