/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.connection;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Andy
 */
public interface Operator {
    enum Operation {ADDITION, MULTIPLICATION, EQUALITY, NONE}
    
    public Operation getOperation();
    public void setOperation(Operation operator);
    public boolean isNegated();
    public void negate();
    public BooleanProperty negateProperty();
}
