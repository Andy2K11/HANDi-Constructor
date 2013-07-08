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
import uk.co.corductive.msc.network.node.AbstractNodeModel;
import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionModel extends AbstractConnectionModel {
    private Date d = new Date();
    private long time;
    
    public ConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2, Operation operation) {
        super(node1, node2, operation);
        time = d.getTime();
    }
    
    public ConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2) {
        super(node1, node2);
    }
    
    @Override
    public JSONObject getJSONObject () {
        jSONObject.put("name", this.getName());
        jSONObject.put("negate", this.isNegated());
        jSONObject.put("operator", this.getOperation().toString());
        jSONObject.put("node1", this.getNode1().getName());
        jSONObject.put("node2", this.getNode2().getName());
        jSONObject.put("date", time);
        return jSONObject;
    }
}
