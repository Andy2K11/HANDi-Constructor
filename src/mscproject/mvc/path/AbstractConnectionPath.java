/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc.path;

import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class AbstractConnectionPath extends Path {
    
    public AbstractConnectionPath() {
        
    }
    
    public abstract void setStartPosition(double x, double y);
    public abstract void setEndPosition(double x, double y);
    public abstract void setControlPosition(double x, double y);
}
