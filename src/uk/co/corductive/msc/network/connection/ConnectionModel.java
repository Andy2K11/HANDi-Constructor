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

import java.util.Date;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.co.corductive.msc.network.node.AbstractNodeModel;
import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionModel extends AbstractConnectionModel {
    private Date d = new Date();
    private long time;
    public static enum Conn {NAME("name"), NEGATE("negate"), OPERATOR("operator"), NODE1("node1"), NODE2("node2"), CONTROLX("controlX"), CONTROLY("controlY"), DATE("date");
        private String string;
        Conn(String string) {
            this.string = string;
        }
        public String getString() {
            return string;
        }
    }
        
    public ConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, Operation operation) {
        super(node1, node2, operation);
        time = d.getTime();
        
        /*
         * For HANDi the y-axis value determines a node's position in the 
         * hierarchy. When the y value of either connected node changes, check
         * if the direction and thus configure parent in relationship.
         */
        directionReversed = new BooleanBinding() {
            {
                super.bind(getNode1().doublePropertyY(), getNode2().doublePropertyY());
            }
            @Override
            protected boolean computeValue() {
                if (getNode2().getY() < getNode1().getY()) return true;
                else return false;
            }
        };
        /*
         * If direction is changed then call x, y positions for both nodes so that
         * values will be recalculated. This will cause change listeners in connection
         * view to update path layout.
         */
        directionReversed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                getNode1().doublePropertyX().getValue();
                getNode1().doublePropertyY().getValue();
                getNode2().doublePropertyX().getValue();
                getNode2().doublePropertyY().getValue();
            }
        });
    }
    
    public ConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2) {
        super(node1, node2);
    }
    
    @Override
    public JSONObject getJSONObject () {
        jSONObject.put(Conn.NAME.getString(), this.getName());
        jSONObject.put(Conn.NEGATE.getString(), this.isNegated());
        jSONObject.put(Conn.OPERATOR.getString(), this.getOperation().toString());
        jSONObject.put(Conn.NODE1.getString(), this.getNode1().getName());
        jSONObject.put(Conn.NODE2.getString(), this.getNode2().getName());
        jSONObject.put(Conn.CONTROLX.getString(), this.getControlX());
        jSONObject.put(Conn.CONTROLY.getString(), this.getControlY());
        jSONObject.put(Conn.DATE.getString(), time);
        return jSONObject;
    }
}
