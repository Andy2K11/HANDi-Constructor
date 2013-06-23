package uk.co.corductive.msc.network.node;

import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.NetworkConnection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class AbstractNodeModel extends AbstractModel implements Operand, NetworkNode, DisplayableNode {
    
    final DoubleProperty value = new SimpleDoubleProperty(0.0);
    final DoubleProperty x = new SimpleDoubleProperty(), y = new SimpleDoubleProperty();
    List<AbstractConnectionModel> list = new ArrayList<>();
    ObservableList connections = FXCollections.observableList(list);
    
    protected AbstractNodeModel() {
        super();
    }

    @Override
    public double getX() {
        return x.doubleValue();
    }

    @Override
    public double getY() {
        return y.doubleValue();
    }
    
    @Override
    public void setX(double x) {
        this.x.set(x);
    }
    
    @Override
    public void setY(double y) {
        this.y.set(y);
    }
    
    @Override
    public DoubleProperty doublePropertyX() {
        return x;
    }

    @Override
    public DoubleProperty doublePropertyY() {
        return y;
    }
    
    @Override
    public double getValue() {
        return this.value.doubleValue();
    }

    @Override
    public void setValue(double value) {
        this.value.set(value);
    }

    @Override
    public void addConnection(NetworkConnection connection) {
        connections.add(connection);
    }

    @Override
    public void removeConnection(NetworkConnection connection) {
        connections.remove(connection);
    }    
}
