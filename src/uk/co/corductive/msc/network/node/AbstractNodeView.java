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

import uk.co.corductive.msc.mvc.AbstractView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import uk.co.corductive.msc.graph.GraphView;

public abstract class AbstractNodeView extends Parent implements AbstractView {
    
    AbstractNodeController controller;
    
    
    
    public static final double DEFAULT_RADIUS = 10.0;
    
    protected AbstractNodeView(AbstractNodeController controller) {
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
        
        this.setOnMouseDragReleased(controller.getOnMouseDragReleased());
        
        controller.getModel().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setTranslateX(newValue.doubleValue());
            }    
        });
        
        controller.getModel().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setTranslateY(newValue.doubleValue());
            }    
        });
    }
    
    @Override
    public AbstractNodeController getController() {
        return controller;
    }
    
    public void remove() {
        ((GraphView)this.getParent()).getChildren().remove(this);
    }
}
