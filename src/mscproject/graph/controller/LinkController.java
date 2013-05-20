package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import mscproject.graph.AbstractLink;
import mscproject.graph.Negatable;
import mscproject.graph.model.LinkModel;
import mscproject.graph.view.AbstractLinkView;
import mscproject.graph.view.Routable;
import mscproject.ui.ToolBarController;
import mscproject.ui.ToolBarController.Tool;

/**
 *
 * @author Andy
 */
public class LinkController extends AbstractController {

    private LinkModel model;
    
    public LinkController (LinkModel model) {
        this.model = model;
        
        model.getView().setOnMouseClicked(handleMouseClicked);
        model.getView().setOnKeyPressed(handleKeyPressed);
        
        model.getView().setOnMouseEntered(handleMouseEntered);
        model.getView().setOnMouseExited(handleMouseExited);
        
        if (model.getView() instanceof Routable) {
            model.getView().setOnDragDetected(handleDragDetected);
        }
    }

    public LinkModel getModel() {
        return model;
    }

    
    
/********************************MOUSE EVENTS*************************************/    
    
    public EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof AbstractLink) {
                AbstractLink link = (AbstractLink) source;
                if (ToolBarController.getSelectedTool()==Tool.delete) {
                    link.remove();
                }
                
                if (event.isControlDown()) {
                    //sl.toggleSelected();
                } else if (source instanceof Negatable) {
                    ((Negatable)link).negate();
                }
            }
            if (source instanceof AbstractLinkView) {
                switch(ToolBarController.getSelectedTool()) {
                    case delete: getModel().delete();    //remove the node and it's links from model    
                        break;
                }
            }
            event.consume();
        }
    };

/********************************DRAG EVENTS**************************************/  
    
public EventHandler handleDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ClipboardContent cbc = new ClipboardContent();
            // a ha - now using MVC we just pass a reference to the node model.
            cbc.putString("");
            Dragboard db;
            Node source = (Node) event.getSource();
            db = source.startDragAndDrop(TransferMode.LINK);
            db.setContent(cbc);
            event.consume();
        }
    };     
    
/********************************KEY EVENTS**************************************/    
    
    public static EventHandler handleKeyPressed = new EventHandler<KeyEvent>() {
        @Override 
        public void handle(KeyEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof AbstractLink) {
                AbstractLink link = (AbstractLink) source;
                if (event.getCode()==KeyCode.DELETE) {
                    link.remove();
                }           
            }
            event.consume();        //if link is focused, then only direct keys at link.
        }
    };

}
