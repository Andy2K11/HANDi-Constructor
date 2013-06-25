/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
