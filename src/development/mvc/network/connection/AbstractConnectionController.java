package development.mvc.network.connection;

import development.mvc.AbstractController;
import development.mvc.network.node.AbstractNodeController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
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
                System.out.println("<MouseEvent> Mouse Clicked");
            }
        };
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                diffX = conn.getPath().getControlX() - event.getX();
                System.out.println("<MouseEvent> Mouse Pressed" + diffX);
                event.consume();
            }
        };
    }
    
    /* The distance between the mouse pointer and the control point when the 
     * drag is started.
     */
    private double diffX;
    
    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                conn.getPath().setControlX(event.getX() + diffX);
                //conn.getPath().setControlX(((AbstractConnectionView)event.getSource()).getPath().getControlX() + event.getX());
                System.out.println("<MouseEvent> Dragged");
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
                //diffX = conn.getPath().getControlX() - event.getX();
                ClipboardContent cbc = new ClipboardContent();
                cbc.putString(String.valueOf(diffX));
                Dragboard db;
                Node source = (Node) event.getSource();
                //db = source.startDragAndDrop(TransferMode.MOVE);
                //db.setContent(cbc);
                //event.consume();
                System.out.println("<MouseEvent> Drag Detected: " + diffX);
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
                System.out.println("<DragEvent> Drag Dropped");
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
