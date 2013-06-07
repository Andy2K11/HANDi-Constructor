/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.connection;

import development.mvc.network.components.AbstractConnectionPath;
import development.mvc.network.components.AdditionConnectionPath;
import development.mvc.network.connection.Operator.Operation;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionViewFactory extends AbstractConnectionViewFactory {

    @Override
    public AbstractConnectionPath createPath(Operation operation) {
        path = new AdditionConnectionPath();
        return path;
    }
    
    @Override
    public AbstractConnectionView createConnecionView(final AbstractConnectionController controller) {
        Operation operator = controller.getModel().getOperation();
        view = new ConnectionView(controller);
        createPath(operator);
        return view;
    }
      
}
