/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.factory;

import javafx.scene.layout.Pane;
import uk.co.corductive.msc.network.connection.AbstractConnectionController;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.connection.ConnectionController;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;
import uk.co.corductive.msc.network.node.AbstractNodeModel;
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
    public AbstractNodeController createNode() {
        AbstractNodeController controller = new NodeController(this);
        return controller;
    }

    @Override
    public AbstractConnectionController createConnection(AbstractNodeController node1, AbstractNodeController node2, Operation operation) {
        AbstractConnectionController controller = new ConnectionController(node1, node2, operation);
        controller.getModel().makeConnection();
        if (operation.equals(Operation.EQUALITY)) {
            controller.getModel().setDirectional(false);
        } else {
            controller.getModel().setDirectional(true);
        }
        controller.getView().createPath(component.getConnectionPath(operation));
        controller.getView().createNegate(component.getNegationPath(operation));
        System.out.println("create connection");
        return controller;
    }
    
    @Override
    public void initConnection(AbstractConnectionController controller, Pane pane) {
        /* Initialize the view with appropriate locations after which observers will
        take care of everything. */
        AbstractConnectionView view = controller.getView();
        AbstractNodeModel source = controller.getModel().getNode1();
        AbstractNodeModel target = controller.getModel().getNode2();
        view.getPath().setStartX(source.getX());
        view.getPath().setStartY(source.getY());
        view.getPath().setEndX(target.getX());
        view.getPath().setEndY(target.getY());
        view.getPath().setControlX(target.getX());
        view.getPath().setControlY(source.getY());
        //view.getPath().updateLayout();
        view.getNegate().setLayoutX(view.getPath().getMiddleX());
        view.getNegate().setLayoutY(view.getPath().getMiddleY());
        view.getNegate().setVisible(controller.getModel().isNegated());
        pane.getChildren().add(view);
        view.toBack();
        System.out.println("init connection");
    }
}
