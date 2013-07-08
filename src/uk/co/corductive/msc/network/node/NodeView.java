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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

/**
 *
 * @author Andy Keavey
 */
public class NodeView extends AbstractNodeView {
    
    private Circle node = new Circle(0, 0, DEFAULT_RADIUS);
    TextField value = new TextField();
    Label name = new Label();
    
    public NodeView(AbstractNodeController controller) {
        super(controller);
        this.getChildren().add(node);
        this.getChildren().add(value);
        //this.getChildren().add(name);
        value.setLayoutX(10.0);
        value.setLayoutY(-30.0);
        value.setPrefColumnCount(8);
        value.textProperty().bindBidirectional(getController().getModel().value);
        value.setVisible(false);
        name.setLayoutX(-70.0);
        name.setLayoutY(-30.0);
        name.setText(getController().getModel().getName());
    }
    
    public void toggleValue() {
        value.setVisible(!value.isVisible());
    }
}
