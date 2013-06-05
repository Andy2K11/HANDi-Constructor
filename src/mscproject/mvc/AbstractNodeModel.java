/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import org.json.JSONObject;

/**
 *
 * @author Andy
 */
public abstract class AbstractNodeModel extends AbstractModel implements InterfaceNetworkNode, InterfaceDisplayableNode {

    private DoubleProperty value;
    private DoubleProperty x, y;
    private List<InterfaceNetworkConnection> connections = new ArrayList<InterfaceNetworkConnection>();
    
    public AbstractNodeModel() {
        
    }

    @Override
    public void addConnection(InterfaceNetworkConnection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeConnection(InterfaceNetworkConnection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject getJSONObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
