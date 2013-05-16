package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mscproject.graph.AbstractLink;
import mscproject.graph.Negatable;
import mscproject.ui.ToolBarController;
import mscproject.ui.ToolBarController.Tool;

/**
 *
 * @author Andy
 */
public class LinkController {

/********************************MOUSE EVENTS*************************************/    
    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
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
            event.consume();
        }
    };

/********************************DRAG EVENTS**************************************/  
    
    
    
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
