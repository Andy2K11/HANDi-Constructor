/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Andy
 */
public interface InterfaceOperator {
    enum Operation {ADDITION, MULTIPLICATION, EQUALITY, NONE}
    
    public Operation getOperation();
    public boolean isNegated();
    public void negate();
    public BooleanProperty negateProperty();
}
