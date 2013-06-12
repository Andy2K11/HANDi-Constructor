package development.mvc.network.connection;

import development.mvc.AbstractController;
import development.mvc.network.node.AbstractNodeController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public abstract class AbstractConnectionController extends AbstractController {
    
    AbstractConnectionModel model;
    AbstractConnectionView view;
    AbstractNodeController node1, node2;
    
    protected AbstractConnectionController(AbstractNodeController node1, AbstractNodeController node2) {
        super();
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public AbstractConnectionModel getModel() {
        return model;
    }

    @Override
    public AbstractConnectionView getView() {
        return view;
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getModel().negate();
                event.consume();
            }
        };
    }
    
    /* The distance between the mouse pointer and the control point when the 
     * drag is started.
     */
    private double diffX = 0.0;
    
    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                conn.getPath().setControlX(event.getX() - diffX);
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                diffX = event.getX() - conn.getPath().getControlX();
                Node source = (Node) event.getSource();
                source.startDragAndDrop(TransferMode.MOVE);
                event.consume();
            } 
        };
    }
    
    
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // to do
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
