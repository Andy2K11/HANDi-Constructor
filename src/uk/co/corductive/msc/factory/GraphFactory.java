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
package uk.co.corductive.msc.factory;

import javafx.scene.control.TextField;
import mscproject.graph.AbstractGraphController;
import mscproject.graph.GraphController;
import mscproject.graph.ScrollTab;

/**
 *
 * @author Andy Keavey
 */
public class GraphFactory {
    
    public ScrollTab createGraph() {
        ScrollTab tab = new ScrollTab();
        AbstractGraphController controller = new GraphController();
        tab.setGraph(controller.getView());
        controller.getModel().nameProperty().bindBidirectional(((TextField)tab.getGraphic()).textProperty());
        
        return tab;
    }
}
