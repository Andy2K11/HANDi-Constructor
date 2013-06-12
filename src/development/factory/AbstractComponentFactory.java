/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.factory;

import development.mvc.network.components.AbstractConnectionPath;
import development.mvc.network.connection.Operator;

/**
 *
 * @author Andy Keavey
 */
public abstract class AbstractComponentFactory {
    
    public abstract AbstractConnectionPath getConnectionPath(Operator.Operation operator);
    
}
