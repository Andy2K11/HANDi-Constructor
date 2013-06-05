/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.scene.Parent;

/**
 *
 * @author Andy
 */
public abstract class AbstractView extends Parent implements InterfaceView {
    InterfaceController controller;
    
    public AbstractView(InterfaceController controller) {
        this.controller = controller;
    }
    
    @Override
    public InterfaceController getController() {
        return controller;
    }
}
