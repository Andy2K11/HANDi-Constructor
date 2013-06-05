/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.scene.shape.Circle;

/**
 *
 * @author Andy
 */
public class GenericNodeView extends AbstractView {
    private static final double RADIUS = 7.0;
    private Circle node = new Circle(0, 0, RADIUS);
    
    public GenericNodeView(InterfaceController controller) {
        super(controller);
        this.getChildren().add(node);
    }
}
