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
package uk.co.corductive.msc.graph;

import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.corductive.msc.mvc.AbstractModel;

/**
 *
 * @author Andy Keavey
 */
public abstract class AbstractGraphModel extends AbstractModel {
    
    AbstractGraphModel() {
        super();
    }
    
    private JSONArray actions = new JSONArray();
    
    public JSONArray recordAction(String actionString, JSONObject object) {
        JSONObject action = new JSONObject();
        action.put("action", actionString);
        action.put("time", System.currentTimeMillis());
        action.put("object", object);
        actions.put(action);
        return actions;
    }
    
    /**
     * Fills the JSON array with data from a loaded file
     * 
     * @param array 
     */
    public void initActions(JSONArray array) {
        if (array instanceof JSONArray) actions = array;
    }
}
