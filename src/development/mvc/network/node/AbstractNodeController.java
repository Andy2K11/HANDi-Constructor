package development.mvc.network.node;

import development.factory.NetworkFactory;
import development.mvc.AbstractController;
import development.mvc.network.connection.AbstractConnectionController;
import development.mvc.network.connection.Operator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.ui.ToolBarController;
import static mscproject.ui.ToolBarController.Tool.copy;
import static mscproject.ui.ToolBarController.Tool.moveone;
import static mscproject.ui.ToolBarController.Tool.movetree;
import static mscproject.ui.ToolBarController.Tool.select;

public abstract class AbstractNodeController extends AbstractController {
    
    /* Node controllers create connections so keep hold of a factory */
    NetworkFactory factory;
    AbstractNodeModel model;
    AbstractNodeView view;
    
    protected AbstractNodeController(NetworkFactory factory) {
        super();
        this.factory = factory;
    }
    
    @Override
    public AbstractNodeView getView() {
        return view;
    }

    @Override
    public AbstractNodeModel getModel() {
        return model;
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // to do
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ClipboardContent cbc = new ClipboardContent();
                cbc.putString("");
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
    }
    
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() instanceof AbstractNodeView && event.getGestureTarget() instanceof AbstractNodeView) {
                    AbstractNodeView source = (AbstractNodeView) event.getGestureSource();
                    AbstractNodeView target = (AbstractNodeView) event.getGestureTarget();
                    AbstractConnectionController controller = factory.createConnection(source.getController(), target.getController(), Operator.Operation.ADDITION);
                    //controller.getView().
                }
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<KeyEvent> getOnKeyPressedHandler() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // to do
                event.consume();
            } 
        };
    }
}
