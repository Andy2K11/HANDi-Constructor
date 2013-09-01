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
package uk.co.corductive.msc.network.connection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;

/**
 * Concrete implementation of a HANDi Connection controller
 * 
 * @author Andy Keavey
 */
public class ConnectionController extends AbstractConnectionController {
    
    public ConnectionController(AbstractNodeController node1, AbstractNodeController node2, Operation operator) {
        super(node1, node2);
        this.model = new ConnectionModel(node1.getModel(), node2.getModel(), operator);
        this.view = new ConnectionView(this);
        
        /*
         * If direction is changed re-initialize positions of paths.
         */
        model.directionReversed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    view.getPath().setEndX(getModel().getNode1().getX());
                    view.getPath().setEndY(getModel().getNode1().getY());
                    view.getPath().setStartX(getModel().getNode2().getX());
                    view.getPath().setStartY(getModel().getNode2().getY());
                } else {
                    view.getPath().setEndX(getModel().getNode2().getX());
                    view.getPath().setEndY(getModel().getNode2().getY());
                    view.getPath().setStartX(getModel().getNode1().getX());
                    view.getPath().setStartY(getModel().getNode1().getY());
                }
            }
        });
    }

}
