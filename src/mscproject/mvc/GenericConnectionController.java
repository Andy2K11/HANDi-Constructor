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
public class GenericConnectionController extends AbstractController {

    GenericConnectionView view;
    AbstractConnectionModel model;
    
    public GenericConnectionController(AbstractConnectionModel model) {
        super(model);
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventHandler<DragEvent> getOnDragDetectedHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventHandler<KeyEvent> getOnKeyPressedHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GenericConnectionView getView() {
        return view;
    }

    @Override
    public AbstractConnectionModel getModel() {
        return model;
    }
    
}
