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

import java.util.List;
import uk.co.corductive.msc.mvc.AbstractView;
import uk.co.corductive.msc.network.components.AbstractConnectionPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.shape.Path;
import uk.co.corductive.msc.graph.GraphView;

/**
 * 
 * 
 * @author Andy Keavey
 */
public abstract class AbstractConnectionView extends Parent implements AbstractView {
    
    AbstractConnectionPath path;
    AbstractConnectionController controller;
    Path negate;
    
    /**
     * Sets all the required handlers for this view and adds change listeners
     * to the positions of the connected nodes. The listeners will check for 
     * the direction of the hierarchical relationship using the model's 
     * isDirectionReversed property, and if it is true, will swap the start and
     * end positions of the connection's path.
     * 
     * @param controller the controller for this view.
     */
    protected AbstractConnectionView(AbstractConnectionController controller) {
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
        
        final AbstractConnectionModel model = controller.getModel();
        
        model.getNode1().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                if (!model.isDirectionReversed()) getPath().setStartX(newValue.doubleValue());
                else getPath().setEndX(newValue.doubleValue());
            }    
        });
        
        model.getNode1().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                if (!model.isDirectionReversed()) getPath().setStartY(newValue.doubleValue());
                else getPath().setEndY(newValue.doubleValue());
            }    
        });
        
        model.getNode2().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                if (!model.isDirectionReversed()) getPath().setEndX(newValue.doubleValue());
                else getPath().setStartX(newValue.doubleValue());
            }    
        });
        
        model.getNode2().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                if (!model.isDirectionReversed()) getPath().setEndY(newValue.doubleValue());
                else getPath().setStartY(newValue.doubleValue());
            }    
        });
        
        
    }
        
    public AbstractConnectionPath getPath() {
        return path;
    }
    
    public Path getNegate() {
        return negate;
    }
        
    /**
     * Creates the visual element of the path (the line going from one node to the
     * other) and listens for removed nodes. If one of the connected nodes removes
     * this link from its list, then the listener registered by this method will
     * tell the other connected node to also remove this link. This is very important
     * to keep each nodes list synchronised.
     * 
     * @param path the path which will represent the connection, usually created
     * by a component factory.
     */
    public void createPath(AbstractConnectionPath path) {
        this.path = path;
        this.getChildren().add(path);
        path.toBack();
        path.getStyleClass().add("link");
        
        controller.getModel().getNode1().getConnections().addListener(new ListChangeListener<NetworkConnection>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if(change.wasRemoved()) {
                    List<NetworkConnection> connections = change.getRemoved();
                    for (NetworkConnection conn: connections) {
                        if(conn.equals((NetworkConnection) controller.getModel())) {
                            remove();
                        }
                    }
                }
            }
        });
        
       controller.getModel().getNode2().getConnections().addListener(new ListChangeListener<NetworkConnection>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if(change.wasRemoved()) {
                    List<NetworkConnection> connections = change.getRemoved();
                    for (NetworkConnection conn: connections) {
                        if(conn.equals((NetworkConnection) controller.getModel())) {
                            remove();
                        }
                    }
                }
            }
        });
       
       /* Note that we are binding the model to the view-path element, not vice versa */
       controller.getModel().doublePropertyControlX().bind(path.doublePropertyControlX());
       controller.getModel().doublePropertyControlY().bind(path.doublePropertyControlY());
    }
    
    public void createNegate(Path negate) {
        this.negate = negate;
        negate.setLayoutX(getPath().middleX().doubleValue());
        getPath().middleX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                getNegate().setLayoutX(newValue.doubleValue());
            }    
        });
        negate.setLayoutY(getPath().middleY().doubleValue());
        getPath().middleY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                getNegate().setLayoutY(newValue.doubleValue());
            }    
        });
        negate.setVisible(controller.getModel().isNegated());
        controller.getModel().negateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                getNegate().setVisible(newValue);
            }
        });
        this.getChildren().add(negate);
        this.negate.toBack();
        negate.getStyleClass().add("link");
    }
    
    @Override
    public AbstractConnectionController getController() {
        return controller;
    }
    
    public void remove() {
        if (this.getParent()!=null) {
            List<Node> list = ((GraphView)this.getParent()).getChildren();
            if (list.contains(this)) {
                    list.remove(this);
            }
        }
    }
}
