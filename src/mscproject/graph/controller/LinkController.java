/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import mscproject.graph.Graph;
import mscproject.graph.DropLink;
import mscproject.ui.MScProjectViewController;

/**
 *
 * @author Andy
 */
public class LinkController {
    
    public static EventHandler handleMouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Node source = (Node) event.getSource();
            if (source instanceof DropLink) {
                DropLink sl = (DropLink) source;
                if (MScProjectViewController.getKeyCode()==KeyCode.DELETE) {
                    sl.getNode1().removeLink(sl);
                    sl.getNode2().removeLink(sl);
                    ((Graph)sl.getParent()).getChildren().remove(sl);
                }
                
                if (event.isControlDown()) {
                    //sl.toggleSelected();
                } else {
                    sl.toggleNegated();
                }
            }
            event.consume();
        }
    };
    

}
