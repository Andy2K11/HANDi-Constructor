/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.connection;

import development.mvc.network.node.AbstractNodeModel;
import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionModel extends AbstractConnectionModel {
    
    public ConnectionModel(AbstractNodeModel node1, AbstractNodeModel node2) {
        super(node1, node2);
    }
    
    @Override
    public JSONObject getJSONObject () {
        return new JSONObject();
    }
}
