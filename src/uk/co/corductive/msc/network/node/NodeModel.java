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
package uk.co.corductive.msc.network.node;

import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.json.JSONObject;

/**
 *
 * @author Andy Keavey
 */
public class NodeModel extends AbstractNodeModel {
    private Date d = new Date();
    private long time;
    private IntegerProperty complex = new SimpleIntegerProperty(0);
    
    public NodeModel() {
        super();
        time = d.getTime();
    }
        
    public void setComplex(int complex) {
        this.complex.set(complex);
    }
    
    public int getComplex() {
        return this.complex.get();
    }
    
    /*
     * 3 = 1, i, -1, -i;
     * 4 = 1, i, -1, -i, 1;
     */
    private static final int COMPLEX_POWER_WRAP = 4;
    public void incrementComplex() {
        if (complex.get() < COMPLEX_POWER_WRAP) {
            complex.set(complex.get() + 1);
        } else {
            complex.set(0);
        }
    }
    
    public IntegerProperty complexIntegerProperty() {
        return complex;
    }
    
    @Override
    public JSONObject getJSONObject () {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", getName());
        jSONObject.put("value", getValue());
        jSONObject.put("x", getX());
        jSONObject.put("y", getY());
        jSONObject.put("complex", getComplex());
        jSONObject.put("date", time);
        return jSONObject;
    }

}
