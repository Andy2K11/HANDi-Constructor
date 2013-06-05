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
public abstract class AbstractConnectionModel extends AbstractModel implements InterfaceNetworkConnection {

    InterfaceNetworkNode node1, node2;
    private BooleanProperty negate = new SimpleBooleanProperty(false);
    private boolean biDirectional = false;
    private boolean directionReversed = false;
    
    public AbstractConnectionModel(InterfaceNetworkNode node1, InterfaceNetworkNode node2) {
        this.node1 = node1;
        this.node2 = node2;
    }
    
    @Override
    public void makeConnection() {
        this.node1.addConnection(this);
        this.node2.addConnection(this);
    }
        
    public AbstractConnectionModel connect() {
        makeConnection();
        return this;
    }   

    @Override
    public InterfaceNetworkNode getNode1() {
        return node1;
    }

    @Override
    public InterfaceNetworkNode getNode2() {
        return node2;
    }

    @Override
    public boolean isBiDirectional() {
        return biDirectional;
    }

    @Override
    public boolean directionReversed() {
        return directionReversed;
    }

    @Override
    public void changeDirection() {
        directionReversed = !directionReversed;
    }

    @Override
    public JSONObject getJSONObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
