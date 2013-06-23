/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.components;

import javafx.beans.binding.DoubleBinding;
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
        
        this.middleX = new DoubleBinding() {
            {
                super.bind(start.xProperty(), end.xProperty(), control.xProperty(), bridge.xProperty());
            }
            @Override
            protected double computeValue() {
                return control.getX() + (bridge.getX() - control.getX())/2;
            }
        };
        this.middleY = new DoubleBinding() {
            {
                super.bind(start.yProperty(), end.yProperty(), control.yProperty(), bridge.yProperty());
            }
            @Override
            protected double computeValue() {
                return control.getY() + (bridge.getY() - control.getY())/2;
            }
        };
        
        this.getElements().addAll(start, control, bridge, end);
    }

    @Override
    public void setStartX(double x) {
        start.setX(x);
    }

    @Override
    public void setStartY(double y) {
        start.setY(y);
        
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);
        
    }

    @Override
    public void setEndY(double y) {
        end.setY(y);
        bridge.setY(y);
        //middleY.invalidate();
    }

    @Override
    public void setControlX(double x) {
        control.setX(x);
        bridge.setX(x);
        //control.setX(end.getX() - (end.getX() - start.getX())/10);
        //bridge.setX(end.getX() - (end.getX() - start.getX())/10);
        //middleX.invalidate();
        
    }

    @Override
    public void setControlY(double y) {
        control.setY(y);
        //control.setY(start.getY());
        //bridge.setY(end.getY());
       // middleY.invalidate();
        // do nothing - control y bound to start y
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
    public void incrementStartY(double dy) {
        super.incrementStartY(dy);
        incrementControlY(dy);
    }

    @Override
    public void incrementEndX(double dx) {
        super.incrementEndX(dx);
        incrementControlX(dx);
        //setEndX(getEndX() + dx);
        //setControlX(getControlX() + dx);
        //bridge.setX(bridge.getX() + dx);
        //middleX.invalidate();
    }
    
    @Override
    public void incrementControlX(double dx) {
        super.incrementControlX(dx);
        bridge.setX(bridge.getX() + dx);
    }
    
    @Override
    public void updateLayout() {
        //.set( control.getX() + (bridge.getX() - control.getX())/2 );
        //middleY.set( control.getY() + (bridge.getY() - control.getY())/2 );
    }
    
    
}
