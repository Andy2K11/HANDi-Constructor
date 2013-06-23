/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.node;

import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class NodeModel extends AbstractNodeModel {
    
    public NodeModel() {
        super();
    }
        
    @Override
    public JSONObject getJSONObject () {
        return new JSONObject();
    }

}
