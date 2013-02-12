package diagram;

import diagram.model.LinkModel;
import diagram.model.NodeModel;
import diagram.view.NodeView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import mscproject.MScProjectViewController;

/**
 *
 * @author Andy
 */
public class DiagramController {
    
    public DiagramController(final Pane diagramView, final DiagramModel diagramModel) {
        
        diagramView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });

        diagramView.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                double x = event.getX();
                double y = event.getY();
                Object source = event.getGestureSource();
                if (source instanceof ToggleButton) {
                    if (((ToggleButton) source).getId().equals("node1button")) {
                        createNewNode(x, y);
                    }
                } else if (source instanceof NodeView) {
                    if (event.getTransferMode()==TransferMode.COPY) {
                        createNewNode(x, y);  
                    } else if (event.getTransferMode()==TransferMode.MOVE) {
                        ((NodeView) source).setLayoutX(x);
                        ((NodeView) source).setLayoutY(y);
                    } else if (event.getTransferMode()==TransferMode.LINK) {
                        System.out.println("Linking");
                        NodeView target = (NodeView) event.getGestureTarget();
                        //LinkView linkView = new LinkView()
                        //LinkModel linkModel = new LinkModel((NodeView)source, (NodeView)target);
                    }
                }
                event.setDropCompleted(true);
                event.consume();
            }
            
            private void createNewNode(double x, double y) {
                // create new node model & create a view (which will create controller
                NodeModel nm = new NodeModel().createView(x, y);
                // add the node model to the diagram model
                diagramModel.getNodes().add(nm);
                // add the node view to the diagram view
                diagramView.getChildren().add(nm.getNodeView());    
            }
        });
        
        diagramView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node target = (Node) event.getTarget();
                //System.out.println("Handler: " + target.toString());
                if (target.getParent() instanceof NodeView) {
                    NodeView nv = (NodeView) target.getParent();
                    NodeModel nm = nv.getNodeController().getNodeModel();
                    if(MScProjectViewController.delete) {
                        diagramModel.deleteNode(nm);
                    }
                }
            }
        });
        
        diagramView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object target = event.getTarget();
                System.out.println("Filter: " + target.toString());
            }
        });
    }
}
