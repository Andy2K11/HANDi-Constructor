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
package uk.co.corductive.msc.factory;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.corductive.msc.graph.AbstractGraphView;
import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.AbstractConnectionModel;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.connection.ConnectionController;
import uk.co.corductive.msc.network.connection.ConnectionModel.Conn;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.node.AbstractNodeModel;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import uk.co.corductive.msc.network.node.NodeController;

/**
 *
 * @author Andy Keavey
 */
public class HandiNetworkFactory implements NetworkFactory {

    HandiComponentFactory component = new HandiComponentFactory();
    
    public HandiNetworkFactory() {
        
    }
    
    @Override
    public AbstractNodeController createNode(double x, double y, Pane pane) {
        AbstractNodeController controller = new NodeController(this);
        controller.getModel().setX(x);
        controller.getModel().setY(y);
        pane.getChildren().add(controller.getView());
        return controller;
    }

    @Override
    public AbstractConnectionController createConnection(AbstractNodeController node1, AbstractNodeController node2, Operation operation, Pane pane) {
        AbstractConnectionController controller = new ConnectionController(node1, node2, operation);
        controller.getModel().makeConnection();
        if (operation.equals(Operation.EQUALITY)) {
            controller.getModel().setDirectional(false);
        } else {
            controller.getModel().setDirectional(true);
        }
        controller.getView().createPath(component.getConnectionPath(operation));
        controller.getView().createNegate(component.getNegationPath(operation));
        initConnection(controller, pane);
        return controller;
    }
    
    @Override
    public void createConnectionsFromJSON(JSONArray jLinks, AbstractGraphView targetTab) {
        if (jLinks!=null) {
            for (int i=0; i<jLinks.length(); i++) {
                JSONObject link = jLinks.getJSONObject(i);
                AbstractNodeController cont1 = null, cont2 = null;

                String name1 = link.getString("node1");
                String name2 = link.getString("node2");

                /* Most of the work here is finding the correct nodes to link to
                * by their name, the nodes must have been added first so we need to 
                * find their actual object reference.
                */
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
                    AbstractConnectionController connController = createConnection(cont1, cont2, op, targetTab);
                    if (link.getBoolean(Conn.NEGATE.getString()) == true) connController.getModel().negate();
                    connController.getView().getPath().setControlX(link.getDouble(Conn.CONTROLX.getString()));
                    connController.getView().getPath().setControlY(link.getDouble(Conn.CONTROLY.getString()));
                }
            }
        }
    }
    
    private void initConnection(AbstractConnectionController controller, Pane pane) {
        /* Initialize the view with appropriate locations after which observers will
        take care of everything. */
        AbstractConnectionView view = controller.getView();
        AbstractNodeModel source = controller.getModel().getNode1();
        AbstractNodeModel target = controller.getModel().getNode2();
        view.getPath().setStartX(source.getX());
        view.getPath().setStartY(source.getY());
        view.getPath().setEndX(target.getX());
        view.getPath().setEndY(target.getY());
        //view.getPath().setControlX(target.getX());
        //view.getPath().setControlY(source.getY());
        view.getPath().middleX().invalidate();
        view.getPath().middleY().invalidate();
        view.getNegate().setLayoutX(view.getPath().getMiddleX());
        view.getNegate().setLayoutY(view.getPath().getMiddleY());
        view.getNegate().setVisible(controller.getModel().isNegated());
        pane.getChildren().add(view);
        view.toBack();
        System.out.println("init connection");
    }
}
