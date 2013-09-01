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
package uk.co.corductive.msc.mvc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.DataFormat;
import org.json.JSONObject;

/**
 * Defines the basic fields and methods for the model element in MVC.
 * 
 * Implementations of this class should be capable of working independently 
 * of the View and Controller elements. Use of properties are used to 
 * allow the Observable design pattern to work through JavaFX listeners.
 * 
 * @author Andy Keavey
 */
public abstract class AbstractModel {
    
    /**
     * Defines a new DataFormat to be used with the Dragboard in Drag-Drop events
     *  where the Serlializable version of json.org libraries can be used.
     */
    public static final DataFormat JSON_FORMAT = new DataFormat("json");
    protected StringProperty name = new SimpleStringProperty();
    
    /**
     * Default no-arg constructor.
     */
    protected AbstractModel() {
        super();
    }
    
    public String getName() {
        return name.getValue();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public abstract JSONObject getJSONObject();
}
