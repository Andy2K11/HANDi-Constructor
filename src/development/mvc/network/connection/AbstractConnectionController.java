package development.mvc.network.connection;

import development.mvc.AbstractController;
import development.mvc.network.node.AbstractNodeController;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class AbstractConnectionController extends AbstractController {
    
    AbstractConnectionModel model;
    AbstractConnectionView view;
    
    protected AbstractConnectionController(AbstractNodeController node1, AbstractNodeController node2) {
        super();
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
                // to do
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
