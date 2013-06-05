/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Andy
 */
public interface InterfaceController {
    public AbstractView getView();
    public InterfaceModel getModel();
    
    public EventHandler<MouseEvent> getOnMouseEnteredHandler();
    public EventHandler<MouseEvent> getOnMouseExitedHandler();
    public EventHandler<MouseEvent> getOnMouseClickedHandler();
    public EventHandler<DragEvent> getOnDragDetectedHandler();
    public EventHandler<DragEvent> getOnDragOverHandler();
    public EventHandler<DragEvent> getOnDragDroppedHandler();
    public EventHandler<KeyEvent> getOnKeyPressedHandler();
}
