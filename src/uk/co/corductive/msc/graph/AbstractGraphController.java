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

import uk.co.corductive.msc.factory.HandiNetworkFactory;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import uk.co.corductive.msc.ui.ToolBarController;

import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.corductive.msc.mvc.AbstractController;
import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.connection.ConnectionModel.Conn;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.node.NodeController;
import uk.co.corductive.msc.network.node.NodeModel;
import uk.co.corductive.msc.network.node.NodeView;

/**
 *
 * @author Andy
 */
public abstract class AbstractGraphController extends AbstractController {
    
    private static NetworkFactory factory = new HandiNetworkFactory();;
    AbstractGraphView view;
    AbstractGraphModel model;
    
    public AbstractGraphController() {
        super();
        
    }

    /**
     * Handles both moving and copying of nodes, and the reshaping of the link path?
     */
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                double x = event.getX();
                double y = event.getY();

                Object source = event.getGestureSource();
                Object target = event.getGestureTarget();
                //out.println("Drag dropped " + target.toString());
                if (target instanceof GraphView) {
                    AbstractGraphView targetTab = (AbstractGraphView) target;
                    if (source instanceof AbstractNodeView) {   
                        if (event.getTransferMode() == TransferMode.COPY) {
                            AbstractNodeController controller;
                            AbstractNodeView view;

                            Dragboard db = event.getDragboard();
                            switch (ToolBarController.getSelectedTool()) {
                                case copy: factory.createNode(event.getX(), event.getY(), targetTab);
                                    break;
                                /*
                                 * Find difference between event position and root node model position
                                 * Adjust all other node positions by this difference
                                 */
                                case copytree:                                
                                    JSONObject jObject = (JSONObject) db.getContent(AbstractModel.JSON_FORMAT);
                                    /* Calculate difference between drag dropped position and original position
                                     * by using the root node of the tree being copied.
                                     */
                                    JSONObject root = jObject.getJSONObject("root");                               
                                    double diffX = event.getX() - root.getDouble("x");
                                    double diffY = event.getY() - root.getDouble("y");

                                    /* Create nodes and replace node names in connection JSON Objects with new names 
                                     * Adjust position of nodes in relation to starting position
                                     */
                                    JSONArray array = jObject.optJSONArray("nodes");
                                    JSONArray conns = jObject.optJSONArray("connections");
                                    for (int i=0; i<array.length(); i++) {
                                        JSONObject node = array.getJSONObject(i);
                                        controller = factory.createNode(node.getDouble("x") + diffX, node.getDouble("y") + diffY, targetTab);
                                        controller.getModel().setValue(node.getString("value"));
                                        if (controller instanceof NodeController) ((NodeModel)controller.getModel()).setComplex(node.getInt("complex"));
                                        /* replace names in connections JSONArray with new names of created nodes */
                                        for (int j=0; j<conns.length(); j++) {
                                            JSONObject conn = conns.getJSONObject(j);
                                            if(conn.getString(Conn.NODE1.getString()).equals(node.getString("name"))) conn.put(Conn.NODE1.getString(), controller.getModel().getName());
                                            if(conn.getString(Conn.NODE2.getString()).equals(node.getString("name"))) conn.put(Conn.NODE2.getString(), controller.getModel().getName());
                                        }
                                    }
                                    /* adjust position of control points on connections */
                                    for (int i=0; i<conns.length(); i++) {
                                        JSONObject conn = conns.getJSONObject(i);
                                        conn.put(Conn.CONTROLX.getString(), conn.getDouble(Conn.CONTROLX.getString()) + diffX);
                                        conn.put(Conn.CONTROLY.getString(), conn.getDouble(Conn.CONTROLY.getString()) + diffY); 
                                    }
                                    /* add connections */
                                    factory.createConnectionsFromJSON(jObject.optJSONArray("connections"), targetTab);
                                    break;
                            }
                        }
                    } else if (event.getTransferMode() == TransferMode.COPY) {
                        // source is tool bar
                        factory.createNode(event.getX(), event.getY(), targetTab);
                    }
                } 
            }
        };
    }

    @Override
    public AbstractGraphModel getModel() {
        return this.model;
    }

    @Override
    public AbstractGraphView getView() {
        return this.view;
    }

    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node source = (Node) event.getSource();
                if (source instanceof GraphView) {
                    GraphView graph = (GraphView) source;
                    switch (ToolBarController.getSelectedTool()) {
                        case create: 
                            switch(ToolBarController.getNodeType()) {
                                case 0: 
                                    factory.createNode(event.getX(), event.getY(), graph);
                                    break;
                            }
                            break;
                        default:    // for all other cases request focus (may enter value into object field)
                        case select:
                            graph.requestFocus();
                            break;
                    }
                }
                event.consume();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node node = (Node) event.getSource();
                if (node instanceof AbstractConnectionView) {
                    
                }
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db;
                Node source = (Node) event.getSource();
                if (source instanceof NodeView) {
                    if (event.isControlDown()) {
                        db = source.startDragAndDrop(TransferMode.COPY);
                    } else if (event.isShiftDown()) {
                        db = source.startDragAndDrop(TransferMode.MOVE);
                    } else {
                        db = source.startDragAndDrop(TransferMode.LINK);
                    }
                    ClipboardContent cbc = new ClipboardContent();
                    db.setContent(cbc);
                } else {
                    //db = source.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                }
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
            }
        };
    }
    
/********************************HELPERS**************************************/   
    
    
}
