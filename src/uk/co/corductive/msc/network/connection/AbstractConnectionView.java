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

public abstract class AbstractConnectionView extends Parent implements AbstractView {
    
    AbstractConnectionPath path;
    AbstractConnectionController controller;
    Path negate;
    
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
        
        AbstractConnectionModel model = controller.getModel();
        model.getNode1().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setStartX(newValue.doubleValue());
                getPath().incrementStartX(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode1().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setStartY(newValue.doubleValue());
                getPath().incrementStartY(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode2().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setEndX(newValue.doubleValue());
                getPath().incrementEndX(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode2().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setEndY(newValue.doubleValue());
                getPath().incrementEndY(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        
    }
        
    public AbstractConnectionPath getPath() {
        return path;
    }
    
    public Path getNegate() {
        return negate;
    }
        
    public void createPath(AbstractConnectionPath path) {
        this.path = path;
        this.getChildren().add(path);
        path.toBack();
        path.updateLayout();    // initialize middle x, y position
        path.getStyleClass().add("link");
        // temp test line -- move to factory
        //createNegate(new Circle(path.getMiddleX(), path.getMiddleY(), 5.0));
        
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
                System.out.println("List change on node1");
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
                System.out.println("List change on node2");
            }
        });
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
        //this.getChildren().remove(this.getNegate());
        //this.getChildren().remove(this.getPath());
        if (this.getParent()!=null) {
            List<Node> list = ((GraphView)this.getParent()).getChildren();
            if (list.contains(this)) {
                    list.remove(this);
            }
        }
        //controller.getModel().getNode1().doublePropertyX().removeListener(this);
    }
}
