/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.json.JSONObject;

/**
 *
 * @author Andy
 */
public class HandiConnectionModel extends GenericConnectionModel implements InterfaceOperator {
    
    Operation operation;
    BooleanProperty negate = new SimpleBooleanProperty(false);
    
    public HandiConnectionModel (InterfaceNetworkNode node1, InterfaceNetworkNode node2, Operation operation) {
        super();
        this.operation = operation;
    }

    @Override
    public JSONObject getJSONObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isNegated() {
        return negate.get();
    }

    @Override
    public void negate() {
        negate.set(!isNegated());
    }
    
    @Override
    public BooleanProperty negateProperty() {
        return negate;
    }    

    @Override
    public Operation getOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
