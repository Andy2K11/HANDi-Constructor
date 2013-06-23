/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.connection;

import uk.co.corductive.msc.network.components.AbstractConnectionPath;
import uk.co.corductive.msc.network.connection.Operator.Operation;

/**
 *
 * @author Andy Keavey
 */
public abstract class AbstractConnectionViewFactory {
    
    AbstractConnectionPath path;
    // Negation negationMark;
    AbstractConnectionView view;
    
    abstract AbstractConnectionPath createPath(Operation operator);
    //abstract Negation createNegation();
    public abstract AbstractConnectionView createConnecionView(final AbstractConnectionController controller);
}
