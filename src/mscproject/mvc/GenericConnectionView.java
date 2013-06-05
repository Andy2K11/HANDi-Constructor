/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import mscproject.mvc.factories.ConnectionPathFactory;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class GenericConnectionView extends AbstractView {
    
    Path connectionPath;
    Path negationMark;
    
    private ConnectionPathFactory cpf = new ConnectionPathFactory();
    
    public GenericConnectionView(GenericConnectionController controller) {
        super(controller);
        //connectionPath = cpf.getConnectionPath(controller.getModel());
        this.getChildren().addAll(connectionPath, negationMark);
    }
}
