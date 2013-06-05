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
public class GenericNodeController extends AbstractController {
    GenericNodeView view;
    AbstractNodeModel model;
    
    public GenericNodeController(AbstractNodeModel model) {
        super(model);
        this.model = model;
        this.view = new GenericNodeView(this);
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
    public AbstractView getView() {
        return view;
    }

    @Override
    public AbstractNodeModel getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
