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

import uk.co.corductive.msc.mvc.AbstractController;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import uk.co.corductive.msc.graph.AbstractGraphView;
import uk.co.corductive.msc.ui.ToolBarController;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.moveone;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.movetree;

public abstract class AbstractConnectionController extends AbstractController {
    
    AbstractConnectionModel model;
    AbstractConnectionView view;
    AbstractNodeController node1, node2;
    
    protected AbstractConnectionController(AbstractNodeController node1, AbstractNodeController node2) {
        super();
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public AbstractConnectionModel getModel() {
        return model;
    }

    @Override
    public AbstractConnectionView getView() {
        return view;
    }
    
    /**
     * Checks if the mouse has been still (not a mouse drag) and then 
     * toggles the model's negate boolean.
     * 
     * @return EventHandler<MouseEvent>
     */
    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch(ToolBarController.getSelectedTool()) {    
                    case delete: 
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("delete-connection", getModel().getJSONObject());
                        remove();
                        break;
                }
                if (event.isStillSincePress()) {
                    getModel().negate();
                }
                event.consume();
            }
        };
    }
    
    public void remove() {
        getModel().removeConnection();
        getView().remove();
    }
    
    /**
     * Checks if press is due to start of mouse drag, if so then a double value 
     * is set that is equal to the x-axis distance between where the mouse event
     * happened and the location of the connection's control point. This is 
     * necessary for Bézier  curves to prevent the curve shape jumping to the 
     * mouse which is necessarily over the actual path.
     * 
     * @return 
     */
    @Override
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isDragDetect()) {
                    AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                    diffX = conn.getPath().getControlX() - event.getX();
                    System.out.println("<MouseEvent> Mouse Pressed" + diffX);
                }
                event.consume();
            }
        };
    }
    
    /* The distance between the mouse pointer and the control point when the 
     * drag is started.
     */
    private double diffX;
    
    /**
     *
     * @return
     */
    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AbstractConnectionView conn = (AbstractConnectionView) event.getSource();
                ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("reshape-connection", getModel().getJSONObject());
                conn.getPath().setControlX(event.getX() + diffX);
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // drag events not used
                event.consume();
            } 
        };
    }
    
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // not used
                event.consume();
            }
        };
    }
    
    @Override
    public EventHandler<KeyEvent> getOnKeyPressedHandler() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // to do
                event.consume();
            } 
        };
    }   
    
    @Override
    public EventHandler<MouseDragEvent> getOnMouseDragReleased() {
        return new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                System.out.println("Connection drag realsed");
                event.consume();
            }
        };
    }
}
