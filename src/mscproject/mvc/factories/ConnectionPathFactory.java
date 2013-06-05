/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc.factories;

import mscproject.mvc.path.AdditionConnectionPath;
import mscproject.mvc.path.AbstractConnectionPath;
import mscproject.mvc.InterfaceOperator.Operation;

/**
 *
 * @author Andy
 */
public class ConnectionPathFactory {
    
    
    public AbstractConnectionPath getConnectionPath(Operation operation) {
        switch(operation) {
            case ADDITION: return new AdditionConnectionPath();
            default: return new AdditionConnectionPath();
        }
    }
}
