/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class AbstractConnectionPath extends Path {
    
    DoubleBinding middleX;
    DoubleBinding middleY;
    
    public abstract void setStartX(double x);
    public abstract void setStartY(double y);
    public abstract void setEndX(double x);
    public abstract void setEndY(double y);
    public abstract void setControlX(double x);
    public abstract void setControlY(double y);
    
    public abstract double getStartX();
    public abstract double getStartY();
    public abstract double getEndX();
    public abstract double getEndY();
    public abstract double getControlX();
    public abstract double getControlY();
    
    public abstract void updateLayout();
    
    //public abstract void incrementStartX(double dx);
    //public abstract void incrementStartY(double dy);
    //public abstract void incrementEndX(double dx);
    //public abstract void incrementEndY(double dy);

    /* Provide some default behaviours for the incrementations. Some paths
     * will override in order to change control positions too.
     */
    public void incrementStartX(double dx) {
        setStartX(getStartX() + dx);
    }

    public void incrementStartY(double dy) {
        setStartY(getStartY() + dy);
    }

    public void incrementEndX(double dx) {
        setEndX(getEndX() + dx);
    }

    public void incrementEndY(double dy) {
        setEndY(getEndY() + dy);
    }   
    
    public void incrementControlX(double x) {
        setControlX(getControlX() + x);
    }
    
    public void incrementControlY(double y) {
        setControlY(getControlY() + y);
    }
    
    public double getMiddleX() {
        return middleX.doubleValue();
    }

    public double getMiddleY() {
        return middleY.doubleValue();
    }

    public DoubleBinding middleX() {
        return middleX;
    }

    public DoubleBinding middleY() {
        return middleY;
    }
}
