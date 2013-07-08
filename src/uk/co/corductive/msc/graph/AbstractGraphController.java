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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.co.corductive.msc.factory.HandiNetworkFactory;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import uk.co.corductive.msc.network.node.NodeView;
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
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.create;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.select;

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
                            case copytree:
                                controller = factory.createNode(event.getX(), event.getY(), targetTab);
                                JSONObject jObject = (JSONObject) db.getContent(AbstractModel.JSON_FORMAT);

                                JSONObject root = jObject.getJSONObject("root");
                                controller.getModel().setValue(root.getString("value"));
                                
                                double rootX = root.getDouble("x");
                                double rootY = root.getDouble("y");
                                
                                Map<String, String> nameMap = new HashMap<>();  // maps original names to newly created names so that we know which nodes to connect
                                nameMap.put(root.getString("name"), controller.getModel().getName());
                                
                                JSONArray array = jObject.getJSONArray("nodes");
                                for (int i=0; i<array.length(); i++) {
                                    JSONObject node = array.getJSONObject(i);
                                    controller = factory.createNode(event.getX() + (node.getDouble("x")) - rootX, event.getY() + (node.getDouble("y")) - rootY, targetTab);
                                    controller.getModel().setValue(node.getString("value"));
                                    nameMap.put(node.getString("name"), controller.getModel().getName());
                                }
                                
                                /* add connections */
                                JSONArray jLinks = jObject.optJSONArray("connections");
                                if (jLinks!=null) {
                                    System.out.println(jLinks.toString(4));
                                    for (int i=0; i<jLinks.length(); i++) {
                                        JSONObject link = jLinks.getJSONObject(i);
                                        AbstractNodeController cont1 = null, cont2 = null;
                                        String name1 = nameMap.get(link.getString("node1"));
                                        String name2 = nameMap.get(link.getString("node2"));
                                        List<Node> nodes = targetTab.getChildren();
                                        for (Node n: nodes) {
                                            if (n instanceof AbstractNodeView) {
                                                if (((AbstractNodeView)n).getController().getModel().getName().equals(name1)) {
                                                    cont1 = ((AbstractNodeView)n).getController();
                                                } else if (((AbstractNodeView)n).getController().getModel().getName().equals(name2)) {
                                                    cont2 = ((AbstractNodeView)n).getController();
                                                }  
                                            }
                                        }
                                        String opString = link.getString("operator");
                                        Operation op = AbstractConnectionModel.stringOperation(opString);
                                        if (cont1==null || cont2 ==null) {
                                            System.err.println("Null node controller");
                                        } else {
                                            factory.createConnection(cont1, cont2, op, targetTab);
                                            System.out.println("Copy complete.");
                                        }
                                    }
                                }
                                break;
                        }
                        }
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
                // to do
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getOnDragDetectedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double oldx = 0;
                ClipboardContent cbc = new ClipboardContent();
                cbc.putString(String.valueOf(event.getX()-oldx));
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
                } else {
                    db = source.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                }
                db.setContent(cbc);
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
