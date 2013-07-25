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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import org.json.JSONObject;
import uk.co.corductive.msc.graph.GraphView;
import static uk.co.corductive.msc.network.node.AbstractNodeView.DEFAULT_RADIUS;

/**
 *
 * @author Andy Keavey
 */
public class NodeView extends AbstractNodeView {
    
    private Circle node = new Circle(0, 0, DEFAULT_RADIUS);
    TextField value = new TextField();
    Label name = new Label();
    Arc complex = new Arc(0, -2*DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, -90, 90);
    
    public NodeView(AbstractNodeController controller) {
        super(controller);
        this.getChildren().addAll(node, value, complex, name);
        
        node.getStyleClass().add("node");
        value.setLayoutX(10.0);
        value.setLayoutY(-30.0);
        
        value.textProperty().bindBidirectional(getController().getModel().value);
        //value.setVisible(false);
        value.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldString, String newString) {
                value.setPrefColumnCount(newString.length());
                
            }
        });
        
        /* we want to record new values, but not on every single value change, only when the user
         * has finished entering a new number. Using the focused property does this, but we won't have
         * access to the old value. Each change will log the value on that change, we know the initial
         * value because if is hardcoded so we can keep a full history.
         */
        value.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                /* if lost focus */
                if (!newValue) {
                    JSONObject obj = getController().getModel().getJSONObject();
                    ((GraphView)getParent()).getController().getModel().recordAction("newvalue", obj);
                }
            }  
        });
        
        /* Override default relation between column count and pref width as text field is made too wide. */
        value.prefWidthProperty().bind(value.prefColumnCountProperty().multiply(7.2).add(20.0));
        value.setPrefColumnCount(3);
        value.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // do something
                getParent().requestFocus();
            } 
        });
        name.setLayoutX(-70.0);
        name.setLayoutY(-30.0);
        name.setText(getController().getModel().getName());
        name.setVisible(false);
        
        complex.lengthProperty().bind(((NodeModel)getController().getModel()).complexIntegerProperty().multiply(90));
        complex.getStyleClass().add("line");
    }
    
    public void toggleValue() {
        value.setVisible(!value.isVisible());
    }
    
    public void setValueVisibility(boolean vis) {
        value.setVisible(vis);
    }
    
    public void toggleName() {
        name.setVisible(!name.isVisible());
    }
}
