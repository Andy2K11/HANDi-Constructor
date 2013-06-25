/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.connection;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Andy
 */
public interface Operator {
    enum Operation {ADDITION, MULTIPLICATION, EQUALITY, NONE}
    
    public Operation getOperation();
    public void setOperation(Operation operator);
    //public Operation stringOperation(String opString);
    public boolean isNegated();
    public void negate();
    public BooleanProperty negateProperty();
}
