/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram;

import diagram.model.NodeModel;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

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
                // create new node model & create a view (which will create controller
                NodeModel nm = new NodeModel().createView(x, y);
                // add the node model to the diagram model
                diagramModel.getNodes().add(nm);
                // add the node view to the diagram view
                diagramView.getChildren().add(nm.getNodeView());
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }
}
