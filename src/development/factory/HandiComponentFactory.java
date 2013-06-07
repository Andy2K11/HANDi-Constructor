/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.factory;

import development.mvc.network.components.AbstractConnectionPath;
import development.mvc.network.components.AdditionConnectionPath;
import development.mvc.network.connection.Operator.Operation;

/**
 *
 * @author Andy
 */
public class HandiComponentFactory {
    
    AbstractConnectionPath path;
    
    public AbstractConnectionPath getConnectionPath(Operation operator) {
        path = new AdditionConnectionPath();
        return path;
    }
}
