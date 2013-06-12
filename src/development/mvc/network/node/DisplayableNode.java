/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.node;

import javafx.beans.property.DoubleProperty;

/**
 *
 * @author Andy
 */
public interface DisplayableNode {
    public double getX();
    public double getY();
    public void setX(double x);
    public void setY(double y);
    public DoubleProperty doublePropertyX();
    public DoubleProperty doublePropertyY();
}
