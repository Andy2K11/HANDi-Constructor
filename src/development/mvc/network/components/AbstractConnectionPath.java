/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.components;

import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class AbstractConnectionPath extends Path {
    
    abstract void setStartPosition(double x, double y);
    public abstract void setEndPosition(double x, double y);
    public abstract void setControlPosition(double x, double y);
}
