/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.components;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class AdditionConnectionPath extends AbstractConnectionPath {

    private MoveTo start = new MoveTo();
    private LineTo control = new LineTo();
    private LineTo end = new LineTo();
    
    public AdditionConnectionPath() {
        super();
        this.getElements().addAll(start, control, end);
    }
    
    @Override
    public void setStartPosition(double x, double y) {
        start.setX(x);
        start.setY(y);
        control.setY(start.getY());
    }

    @Override
    public void setEndPosition(double x, double y) {
        end.setX(x);
        end.setY(y);
        control.setX(end.getX());
    }

    @Override
    public void setControlPosition(double x, double y) {
        control.setX(x);
        control.setY(start.getY());
    }
    
}
