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
import mscproject.graph.Graph;
import mscproject.graph.factory.LinkFactory;
import mscproject.graph.model.LinkModel.LinkType;
import mscproject.graph.model.NodeModel;
import mscproject.graph.view.AbstractLinkView;
import mscproject.graph.view.NodeView;
import uk.co.corductive.msc.ui.ToolBarController;

/**
 *
 * @author Andy
 */
public class NodeController extends AbstractController {
        // Keep a record of all nodes in a set, use for checking uniqness.
    private static Set<NodeModel> nodes = new TreeSet<>();
    
    private static DataFormat dataFormat = new DataFormat("node");
    private NodeModel model;
    private static NodeModel source;  // dragboard data is copied and will not give reference to actual source object
    //private NodeView view;
    
    private static LinkFactory linkFactory = new LinkFactory();
    
    
    public NodeController(NodeModel nodeM, NodeView view) {
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

    public void saveTree() {
        
    }
    
    public NodeModel getModel() {
        return model;
    }

    private static void setSource(NodeModel nm) {
        source = nm;
    }
    
    private static NodeModel getSource() {
        return source;
    }
    
/********************************MOUSE EVENTS*************************************/
    
    
    public EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
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
            setSource(getModel());
            ClipboardContent cbc = new ClipboardContent();
            // a ha - now using MVC we can put model DATA on dragboard, but not ref!!
            cbc.put(dataFormat, getModel());
            Dragboard db;
            Node source = (Node) event.getSource();
            switch (ToolBarController.getSelectedTool()) {
                case moveone:
                case movetree: db = source.startDragAndDrop(TransferMode.MOVE);
                    break;
                case copy: db = source.startDragAndDrop(TransferMode.COPY);
                    break;
                case select: db = source.startDragAndDrop(TransferMode.LINK);
                    break;
                default: db = source.startDragAndDrop(TransferMode.ANY);
            }
            
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
            
            Dragboard db = event.getDragboard();
            Object o = db.getContent(dataFormat);       // what happens if wrong DataFormat? put inside type check?
            
            if (source instanceof NodeView && target instanceof NodeView) {
                NodeView sourceNV = (NodeView) source;
                NodeView targetNV = (NodeView) target;
                
                NodeModel targetNM = getModel();
                // Is this actually getting a ref to our node model? No!
                NodeModel sourceNM;
                if (false && o instanceof NodeModel) {  // DISABLED
                    sourceNM = (NodeModel) o;    
                     /* Because the view held by a model is made transient it is null after
                     * been placed on the drag board. */
                    sourceNM.setView(sourceNV);
                } else { 
                    sourceNM = getSource();
                }         

                switch(event.getTransferMode()) {
                    case COPY:
                    case MOVE:
                    case LINK:
                    // get the selected link type on the toolbar and convert it to a type the link factory understands
                    LinkType linkType = linkTypeAdapter(ToolBarController.getLinkType());
                    // create a new link using the factory and return it's view. 
                    AbstractLinkView linkView = linkFactory.makeLink(sourceNM, targetNM, linkType).getModel().getView();
                    // add the view to the parent pane
                    ((Graph)sourceNV.getParent()).getChildren().add(linkView);
                    linkView.toBack();  // put the links behind nodes so that user can iteract with nodes
                    break;
                }
            } 
            event.consume();

        }
    };
    
    /*
     * Helper method that converts an int value from a selection type control
     * and converts it into a type of link.
     */
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
    
    
    
    public EventHandler handleKeyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            Object source = event.getSource();
            if (source instanceof NodeView) {
                switch(event.getCode()) {
                    case DELETE: getModel().delete();
                        break;
                }
            }
            event.consume();
        }
    };
}
