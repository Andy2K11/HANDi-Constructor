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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import uk.co.corductive.msc.mvc.AbstractView;

/**
 *
 * @author Andy Keavey
 */
public abstract class AbstractGraphView extends Pane implements AbstractView {
    private StringProperty name = new SimpleStringProperty();
    AbstractGraphController controller;
    
    AbstractGraphView(AbstractGraphController controller) {
        super();
        this.controller = controller;
        
        this.setOnMouseEntered(controller.getOnMouseEnteredHandler());
        this.setOnMouseExited(controller.getOnMouseExitedHandler());
        this.setOnMouseClicked(controller.getOnMouseClickedHandler());
        this.setOnMousePressed(controller.getOnMousePressedHandler());
        this.setOnMouseDragged(controller.getOnMouseDraggedHandler());
        
        this.setOnDragDetected(controller.getOnDragDetectedHandler());
        this.setOnDragOver(controller.getOnDragOverHandler());
        this.setOnDragDropped(controller.getOnDragDroppedHandler());

        this.setOnKeyPressed(controller.getOnKeyPressedHandler());
    }
    
    @Override
    public AbstractGraphController getController() {
        return controller;
    }
    
    public final String getName() {
        return name.get();
    }
    
    public final void setName(String name) {
        this.name.set(name);
    }
    
    public final StringProperty nameProperty() {
        return name;
    }
}
