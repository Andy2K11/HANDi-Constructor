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
    private LineTo bridge = new LineTo();
    private LineTo end = new LineTo();
    
    public AdditionConnectionPath() {
        super();
        this.getElements().addAll(start, control, bridge, end);
    }

    @Override
    public void setStartX(double x) {
        start.setX(x);
    }

    @Override
    public void setStartY(double y) {
        start.setY(y);
        control.setY(start.getY());
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);
        control.setX(end.getX() - (end.getX() - start.getX())/10);
        bridge.setX(end.getX() - (end.getX() - start.getX())/10);
    }

    @Override
    public void setEndY(double y) {
        end.setY(y);
        bridge.setY(end.getY());
    }

    @Override
    public void setControlX(double x) {
        control.setX(x);
    }

    @Override
    public void setControlY(double y) {
        // do nothing - control y bound to start y
        //control.setY(start.getY());
    }

    @Override
    public double getControlX() {
        return control.getX();
    }

    @Override
    public double getControlY() {
        return control.getY();
    }

    @Override
    public double getStartX() {
        return start.getX();
    }

    @Override
    public double getStartY() {
        return start.getY();
    }

    @Override
    public double getEndX() {
        return end.getX();
    }

    @Override
    public double getEndY() {
        return end.getY();
    }

    @Override
    public void updateLayout() {
        middleX.set( control.getX() + (bridge.getX() - control.getX())/2 );
        middleY.set( control.getY() + (bridge.getY() - control.getY())/2 );
    }
}
