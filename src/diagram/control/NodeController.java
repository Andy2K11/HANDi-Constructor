package diagram.control;

import diagram.model.NodeModel;
import diagram.view.NodeView;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.MScProjectViewController;

/**
 *
 * @author Andy
 */
public class NodeController {
    //private final NodeView nodeView;
    //private final NodeModel nodeModel;
    
    public NodeController(final NodeView nodeView, final NodeModel nodeModel) {
        //this.nodeView = nv;
        //this.nodeModel = nm;
        
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
        
        nodeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked on node");
                if(MScProjectViewController.delete) {
                   //nodeModel.deleteNode();
                }
            }
        });
    }
}
