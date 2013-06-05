/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

/**
 *
 * @author Andy
 */
public interface InterfaceNetworkNode {
    public void addConnection(InterfaceNetworkConnection connection);
    public void removeConnection(InterfaceNetworkConnection connection);
}
