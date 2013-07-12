/*
 * Copyright (C) 2013 Andy Keavey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.corductive.msc.network.connection;

import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.node.AbstractNodeModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.json.JSONObject;
import uk.co.corductive.msc.network.node.NetworkNode;

public abstract class AbstractConnectionModel extends AbstractModel implements Operator, NetworkConnection, DisplayableConnection {
    
    protected Operation operator;
    AbstractNodeModel node1, node2;
    BooleanProperty negate = new SimpleBooleanProperty(false);
    BooleanProperty directional = new SimpleBooleanProperty(false);
    BooleanProperty directionReversed = new SimpleBooleanProperty(false);
    DoubleProperty controlX = new SimpleDoubleProperty();
    DoubleProperty controlY = new SimpleDoubleProperty();
    JSONObject jSONObject = new JSONObject();
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, Operation operator) {
        super();
        this.node1 = node1;
        this.node2 = node2;
        this.operator = operator;
        setName(node1.getName() + "-" + node2.getName());
    }
    
    protected AbstractConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, String operator) {
        this(node1, node2, Operation.NONE);
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
    
    public static Operation stringOperation(String opString){
        switch(opString) {
            case "EQUALITY": return Operation.EQUALITY;
            case "MULTIPLICATION": return Operation.MULTIPLICATION;
            case "ADDITION": return Operation.ADDITION;
            default: return Operation.NONE;
        }
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
    public AbstractNodeModel getNode1() {
        return node1;
    }

    @Override
    public AbstractNodeModel getNode2() {
        return node2;
    }
    
    @Override
    public AbstractNodeModel getOtherNode(NetworkNode node) {
        if ( node.equals(getNode1()) ) {
            return getNode2();
        } else {
            return getNode1();
        } 
    }

    @Override
    public boolean isDirectional() {
        return directional.get();
    }

    @Override
    public void setDirectional(boolean direction) {
        this.directional.set(direction);
    }
    
    @Override
    public boolean isDirectionReversed() {
        return directionReversed.get();
    }

    @Override
    public void changeDirection() {
        directionReversed.set(!isDirectionReversed());
    }

    @Override
    public boolean isParent(NetworkNode node) {
        if (this.isDirectional()) {
            if ( (node.equals(getNode1()) && !isDirectionReversed()) || (node.equals(getNode2()) && isDirectionReversed()) ) {
                return true;
            }
        } 
        return false;
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

    @Override
    public double getControlX() {
        return controlX.doubleValue();
    }

    @Override
    public double getControlY() {
        return controlY.doubleValue();
    }

    @Override
    public DoubleProperty doublePropertyControlX() {
        return controlX;
    }

    @Override
    public DoubleProperty doublePropertyControlY() {
        return controlY;
    }
}
