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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.mvc.AbstractController;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import org.json.JSONObject;
import uk.co.corductive.msc.graph.AbstractGraphView;
import uk.co.corductive.msc.mvc.AbstractModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.NetworkConnection;
import uk.co.corductive.msc.ui.ToolBarController;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.copy;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.moveone;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.movetree;

public abstract class AbstractNodeController extends AbstractController {
    
    /* Node controllers create connections so keep hold of a factory */
    NetworkFactory factory;
    AbstractNodeModel model;
    AbstractNodeView view;
    double maxX, maxY;
    private Set<NetworkNode> tree;
    
    protected AbstractNodeController(NetworkFactory factory) {
        super();
        this.factory = factory;
    }
    
    @Override
    public final AbstractNodeView getView() {
        return view;
    }

    @Override
    public final AbstractNodeModel getModel() {
        return model;
    }
    
    
    /**
     * Removes all the network connections attached to this node.
     */
    public void remove() {
        this.getView().remove();
        for (NetworkConnection conn: this.getModel().getConnections()) {
            conn.removeConnection((NetworkNode)this.getModel());
        }
        getModel().getConnections().clear();
        // connection views are removed via list change listener
    }
    
    double dragOriginX;
    double dragOriginY;
    /**
     * Records the x, y, position which will define the origin in a potential 
     * mouse drag event.
     * 
     * @return mouse pressed handler 
     */
    @Override
    public EventHandler<MouseEvent> getOnMousePressedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragOriginX = event.getX();
                dragOriginY = event.getY();
                tree = getModel().getSubNodeTree();
                Bounds bounds = getView().getParent().getBoundsInLocal();
                maxX = bounds.getMaxX();
                maxY = bounds.getMaxY();
                event.consume();
            }
        };
    }
    
    /**
     * Handles the moving of objects, either singularly or as trees, around the diagram.
     * 
     * @return drag handler
     */
    @Override
    public EventHandler<MouseEvent> getOnMouseDraggedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                switch (ToolBarController.getSelectedTool()) {
                    case movetree:
                        boolean outOfBounds = false;
                        for (NetworkNode node: tree) {
                            AbstractNodeModel aNode = (AbstractNodeModel) node;
                            if (aNode.getX() + event.getX() < 0.0 || aNode.getY() + event.getY() < 0.0
                                    || getModel().getX() + event.getX() < 0.0 || getModel().getY() + event.getY() < 0.0
                                    || aNode.getX() + event.getX() > maxX || aNode.getY() + event.getY() > maxY
                                    || getModel().getX() + event.getX() > maxX || getModel().getY() + event.getY() > maxY) outOfBounds = true;
                        }
                        if (!outOfBounds) {
                            for (NetworkNode node: tree) {
                                AbstractNodeModel aNode = (AbstractNodeModel) node;
                                aNode.setX(aNode.getX() + event.getX());
                                aNode.setY(aNode.getY() + event.getY());
                            }
                            getModel().setX(getModel().getX() + event.getX());
                            getModel().setY(getModel().getY() + event.getY());
                        }
                        
                        break;
                    case moveone:
                        getModel().setX(getModel().getX() + event.getX());
                        getModel().setY(getModel().getY() + event.getY());
                        
                        break;
                }
                event.consume();
            }
        };
    }
    
    /**
     * Used only for recording events when a move has been completed
     * 
     * @return a drag released handler.
     */
    @Override
    public EventHandler<MouseDragEvent> getOnMouseDragReleased() {
        return new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                switch (ToolBarController.getSelectedTool()) {
                    case movetree:
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("movetree_done-node", getModel().getJSONObject());
                        break;
                    case moveone:
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("moveone_done-node", getModel().getJSONObject());
                        break;
                }
                System.out.println("Mouse drag released.");
                event.consume();
            }
        };
    }
    
    /**
     * Starts a full drag for move actions or a drag-drop for copy actions.
     * Copy actions will put JSON data representing the copied object onto
     * the dragboard
     * 
     * @return a drag detected handler
     */
    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ClipboardContent cbc = new ClipboardContent();
                cbc.putString(String.valueOf(getModel().getValue()));
                Dragboard db;
                Node source = (Node) event.getSource();
                switch (ToolBarController.getSelectedTool()) {
                    case moveone: source.startFullDrag();
                    ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("moveone_start-node", getModel().getJSONObject());
                        break;
                    case movetree: source.startFullDrag(); // as opposed to simple drag which won't fire
                    ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("movetree_start-node", getModel().getJSONObject());
                    // event to other nodes.
                        // nb: this case is required to prevent default action
                        break;
                    case copy: db = source.startDragAndDrop(TransferMode.COPY);
                        //cbc.putString(getModel().getJSONObject().toString());
                        db.setContent(cbc);
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("copy_start-node", getModel().getJSONObject());
                        break;
                    case copytree: db = source.startDragAndDrop(TransferMode.COPY);
                        JSONObject j = new JSONObject();
                        j.put("root", getModel().getJSONObject());
                        j.append("nodes", getModel().getJSONObject());
                        Set<NetworkNode> ts = getModel().getSubNodeTree();
                        Set<NetworkConnection> connSet= new HashSet<>();    // using a set will prevent duplicates
                        for (NetworkNode n: ts) {
                            AbstractNodeModel nm = (AbstractNodeModel) n;
                            j.append("nodes", nm.getJSONObject() );
                            List<NetworkConnection> list = nm.getConnections();
                            for (NetworkConnection nc: list) {
                                connSet.add(nc);
                            }
                        }
                        for (NetworkConnection nc: connSet) {
                            AbstractConnectionModel model = (AbstractConnectionModel) nc;
                            j.append("connections", model.getJSONObject());
                        }
                        cbc.put(AbstractModel.JSON_FORMAT, j);
                        db.setContent(cbc);
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("copytree_start-node", getModel().getJSONObject());
                        break;                        
                    default: // If not a move or copy then assume link is to be created.
                        db = source.startDragAndDrop(TransferMode.LINK);
                        db.setContent(cbc);
                        break;
                }
                event.consume();
            } 
        };
    }
    
    /**
     * A drag drop on to a node occurs while making connections. This method gets
     * the source and target node from the drag gesture and invokes the appropriate
     * factory method.
     * 
     * @return on drag dropped handler
     */
    @Override
    public EventHandler<DragEvent> getOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                String content = (String) db.getContent(DataFormat.PLAIN_TEXT);
                if (event.getTransferMode() == TransferMode.LINK) {
                    if (event.getGestureSource() instanceof AbstractNodeView && event.getGestureTarget() instanceof AbstractNodeView) {
                        AbstractNodeView source = (AbstractNodeView) event.getGestureSource();
                        AbstractNodeView target = (AbstractNodeView) event.getGestureTarget();
                        factory.createConnection(source.getController(), target.getController(), linkTypeAdapter(ToolBarController.getLinkType()), (Pane)source.getParent());
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("create-connection", getModel().getJSONObject());
                    }
                } else if (event.getTransferMode() == TransferMode.COPY) {
                    switch (ToolBarController.getSelectedTool()) {
                        case copy: // do nothing       
                    }
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
                
                event.consume();
            } 
        };
    }
    
    /*
     * Helper method that converts an int value from a selection type control
     * and converts it into a type of link.
     */
    private Operation linkTypeAdapter(int selectedLink) {
        switch(selectedLink) {
            case 0: return Operation.EQUALITY;
            case 1: return Operation.ADDITION;
            case 2: return Operation.MULTIPLICATION;
            case 3: ;
            default: return Operation.NONE;
        }
    }
    
    
    
}
