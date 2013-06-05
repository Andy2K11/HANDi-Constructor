/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc.factories;

import mscproject.mvc.AbstractNodeModel;
import mscproject.mvc.InterfaceController;
import mscproject.mvc.InterfaceOperator.Operation;

/**
 *
 * @author Andy
 */
public interface NetworkFactory {
    public InterfaceController createNode();
    public InterfaceController createConnection(AbstractNodeModel node1, AbstractNodeModel node2, Operation operation);
}
