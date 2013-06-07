package development.mvc.network.connection;

import development.mvc.AbstractModel;
import development.mvc.network.node.AbstractNodeModel;
import javafx.beans.property.BooleanProperty;

public abstract class AbstractConnectionModel extends AbstractModel implements Operator {
    
    protected Operation operator;
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, Operation operator) {
        super();
        this.operator = operator;
    }
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2) {
        this(node1, node2, Operation.NONE);
    }

    @Override
    public Operation getOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOperation(Operation operator) {
        this.operator = operator;
    }
    @Override
    public boolean isNegated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void negate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BooleanProperty negateProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
