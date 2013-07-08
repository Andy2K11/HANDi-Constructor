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
package mscproject.graph;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import org.json.*;

/**
 *
 * @author Andy
 */
public class GraphView extends AbstractGraphView {
    
        
    public GraphView(AbstractGraphController controller) {
        super(controller);
        this.setMinSize(1200, 800);
        this.setMaxSize(1200, 800);
        this.getStyleClass().add("graph");
        this.getStylesheets().add("resources/graph.css");
    }
    

     
    public JSONObject getJSONObject() {
        JSONObject jGraph = new JSONObject();
        for(Node node: this.getChildren()) {
            
        }
        return jGraph;
    }
}
