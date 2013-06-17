package development.mvc.network.node;

import development.factory.NetworkFactory;
import development.mvc.AbstractController;
import development.mvc.network.connection.AbstractConnectionController;
import development.mvc.network.connection.AbstractConnectionView;
import development.mvc.network.connection.Operator.Operation;
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
    double dragOriginX;
    double dragOriginY;
    @Override
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragOriginX = event.getX();
                dragOriginY = event.getY();
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node source = (Node) event.getSource();
                double x = event.getSceneX();
                double y = event.getSceneY();
                switch (ToolBarController.getSelectedTool()) {
                        case movetree: //moveTree(sourceNode, x, y);
                            break;
                        case moveone:
                            //((AbstractNodeView)source).getController().getModel().setX(event.getX());
                            //((AbstractNodeView)source).getController().getModel().setY(event.getY());
                            getModel().setX(getModel().getX() + event.getX());
                            getModel().setY(getModel().getY() + event.getY());
                            break;
                    }
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
                    case movetree: //db = source.startDragAndDrop(TransferMode.MOVE);
                        break;
                    case copy: db = source.startDragAndDrop(TransferMode.COPY);
                        db.setContent(cbc);
                        break;
                    case select: db = source.startDragAndDrop(TransferMode.LINK);
                        db.setContent(cbc);
                        break;
                    default: db = source.startDragAndDrop(TransferMode.ANY);
                        db.setContent(cbc);
                }
                event.consume();
            } 
        };
    }
    
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                db.getContent(DataFormat.RTF);
                if (event.getTransferMode() == TransferMode.LINK) {
                    if (event.getGestureSource() instanceof AbstractNodeView && event.getGestureTarget() instanceof AbstractNodeView) {
                        AbstractNodeView source = (AbstractNodeView) event.getGestureSource();
                        AbstractNodeView target = (AbstractNodeView) event.getGestureTarget();
                        AbstractConnectionController controller 
                                = factory.createConnection(source.getController(), target.getController(), linkTypeAdapter(ToolBarController.getLinkType()));
                        
                        /* Initialize the view with appropriate locations after which observers will
                         take care of everything. */
                        AbstractConnectionView view = controller.getView();
                        view.getPath().setStartX(source.getController().getModel().getX());
                        view.getPath().setStartY(source.getController().getModel().getY());
                        view.getPath().setEndX(target.getController().getModel().getX());
                        view.getPath().setEndY(target.getController().getModel().getY());
                        view.getPath().setControlX(target.getController().getModel().getX());
                        view.getPath().setControlY(source.getController().getModel().getY());
                        view.getPath().updateLayout();
                        view.getNegate().setLayoutX(view.getPath().getMiddleX());
                        view.getNegate().setLayoutY(view.getPath().getMiddleY());
                        view.getNegate().setVisible(controller.getModel().isNegated());
                        ((Graph)source.getParent()).getChildren().add(view);
                        view.toBack();
                    }
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
                
                event.consume();
            } 
        };
    }
    
        /*
     * Helper method that converts an int value from a selection type control
     * and converts it into a type of link.
     */
    private Operation linkTypeAdapter(int selectedLink) {
        switch(selectedLink) {
            case 0: return Operation.EQUALITY;
            case 1: return Operation.ADDITION;
            case 2: return Operation.MULTIPLICATION;
            case 3: ;
            default: return Operation.NONE;
        }
    }
    
}
