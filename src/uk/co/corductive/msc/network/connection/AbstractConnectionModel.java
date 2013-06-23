package uk.co.corductive.msc.network.connection;

import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.node.AbstractNodeModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.json.JSONObject;
import uk.co.corductive.msc.network.node.NetworkNode;

public abstract class AbstractConnectionModel extends AbstractModel implements Operator, NetworkConnection {
    
    protected Operation operator;
    AbstractNodeModel node1, node2;
    BooleanProperty negate = new SimpleBooleanProperty(false);
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, Operation operator) {
        super();
        this.node1 = node1;
        this.node2 = node2;
        this.operator = operator;
    }
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2) {
        this(node1, node2, Operation.NONE);
    }

    @Override
    public Operation getOperation() {
        return operator;
    }

    @Override
    public void setOperation(Operation operator) {
        this.operator = operator;
    }
    
    @Override
    public boolean isNegated() {
        return negate.get();
    }

    @Override
    public void negate() {
        negate.set(!negate.get());
    }

    @Override
    public BooleanProperty negateProperty() {
        return negate;
    }

    @Override
    public JSONObject getJSONObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractNodeModel getNode1() {
        return node1;
    }

    @Override
    public AbstractNodeModel getNode2() {
        return node2;
    }

    @Override
    public boolean isBiDirectional() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean directionReversed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeDirection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void makeConnection() {
        node1.addConnection(this);
        node2.addConnection(this);
    }
    
    @Override
    public void removeConnection() {
        node1.removeConnection(this);
        node2.removeConnection(this);
    }
    
    
    @Override
    public void removeConnection(NetworkNode node) {
        if (((NetworkNode)node1).equals(node)) {
            node2.removeConnection(this);
        } else if (((NetworkNode)node2).equals(node)) {
            node1.removeConnection(this);
        } else {
            System.err.println("Error removing connection");
        }
    }
}
