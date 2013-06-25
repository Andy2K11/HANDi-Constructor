/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.node;

import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class NodeModel extends AbstractNodeModel {
    private Date d = new Date();
    private long time;
    
    public NodeModel() {
        super();
        time = d.getTime();
    }
        
    @Override
    public JSONObject getJSONObject () {
        jSONObject.put("name", getName());
        jSONObject.put("value", getValue());
        jSONObject.put("x", getX());
        jSONObject.put("y", getY());
        jSONObject.put("date", time);
        return jSONObject;
    }

}
