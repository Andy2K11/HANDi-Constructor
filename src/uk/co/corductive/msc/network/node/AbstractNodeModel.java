package uk.co.corductive.msc.network.node;

import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.NetworkConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public abstract class AbstractNodeModel extends AbstractModel implements Operand, NetworkNode, DisplayableNode, Comparable<AbstractNodeModel> {
    
    final StringProperty value = new SimpleStringProperty("0.0");
    final DoubleProperty x = new SimpleDoubleProperty(), y = new SimpleDoubleProperty();
    List<AbstractConnectionModel> list = new ArrayList<>();
    ObservableList connections = FXCollections.observableList(list);
    JSONObject jSONObject = new JSONObject();
    private static int nodeNum = 0;
    
    protected AbstractNodeModel() {
        super();
        nodeNum++;
        setName("node_"+nodeNum);
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
    public String getValue() {
        return this.value.getValue();
    }

    @Override
    public void setValue(String value) {
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
    
    public ObservableList<NetworkConnection> getConnections() {
        return connections;
    }
    
    @Override
    public Set<NetworkNode> getSubNodeTree() {
        Set<NetworkNode> tree = new TreeSet<>();
        tree = buildTree(tree);
        System.out.println("Tree size " + tree.size());
        return tree;
    }
    
    /*
     * Does the work of building the tree. Should take an empty tree as argument
     * as supplied by the public method getSubNodeTree().
     */
    private Set<NetworkNode> buildTree(Set<NetworkNode> tree) {
        for (NetworkConnection conn: getConnections()) {
            if (conn.isParent(this)) {
                NetworkNode node = (NetworkNode) conn.getOtherNode(this);
                tree.add(node);
                tree.addAll(((AbstractNodeModel)node).buildTree(tree));
            }
        }
        return tree;
    }
    
    @Override
    public int compareTo(AbstractNodeModel node) {
        if (node.getY() < this.getY()) return 1;
        else if (node.getY() > this.getY()) return -1;
        else {
            if (node.getX() < this.getX()) return 1;
            else if (node.getX() > this.getX()) return -1;
            else return 0;
        }
    }
    
    @Override
    public boolean equals(Object node) {
        if (node instanceof AbstractNodeModel) {
            AbstractNodeModel aNode = (AbstractNodeModel) node;
            if ( this.getX() == aNode.getX() && this.getY() == aNode.getY()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.value);
        hash = 59 * hash + Objects.hashCode(this.x);
        hash = 59 * hash + Objects.hashCode(this.y);
        return hash;
    }
}
