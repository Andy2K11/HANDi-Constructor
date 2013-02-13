package diagram.control;

import diagram.model.LinkModel;
import diagram.model.NodeModel;
import diagram.view.NodeView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.ui.MScProjectViewController;

/**
 *
 * @author Andy
 */
public class NodeController {
    private final NodeModel nodeModel;
    
    public NodeController(final NodeView nodeView, final NodeModel nodeModel) {
        //this.nodeView = nv;
        this.nodeModel = nodeModel;
        
        nodeView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ClipboardContent cbc = new ClipboardContent();
                cbc.putString("");
                Dragboard db;                
                if (event.isControlDown()) {
                    db = nodeView.startDragAndDrop(TransferMode.COPY);
                } else if (event.isShiftDown()) {
                    db = nodeView.startDragAndDrop(TransferMode.MOVE);
                } else {
                    db = nodeView.startDragAndDrop(TransferMode.LINK);
                }
                db.setContent(cbc);
            }
    
        });
        
        nodeView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.LINK);
                event.consume();
            }
        });
        
        nodeView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Node source = (Node) event.getSource();
                Node target = (Node) event.getTarget();
                if (source instanceof NodeView && target instanceof NodeView) {
                    NodeView nvs = (NodeView) event.getSource();
                    NodeView nvt = (NodeView) event.getTarget();
                    //LinkModel lm = new LinkModel(nvs.getNodeController().getNodeModel(), 
                            //nvt.getNodeController().getNodeModel());
                }
            }
        });
        
        nodeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked on node");
                if(MScProjectViewController.delete) {
                   nodeModel.deleteNode();
                }
                //event.consume();
            }
        });
    }
    
    public NodeModel getNodeModel() {
        return nodeModel;
    }
}
