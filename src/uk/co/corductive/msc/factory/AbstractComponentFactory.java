/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.factory;

import uk.co.corductive.msc.network.components.AbstractConnectionPath;
import uk.co.corductive.msc.network.connection.Operator;

/**
 *
 * @author Andy Keavey
 */
public abstract class AbstractComponentFactory {
    
    public abstract AbstractConnectionPath getConnectionPath(Operator.Operation operator);
    
}
