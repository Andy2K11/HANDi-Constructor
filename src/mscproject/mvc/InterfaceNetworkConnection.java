/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.mvc;

/**
 *
 * @author Andy
 */
public interface InterfaceNetworkConnection {
    public InterfaceNetworkNode getNode1();
    public InterfaceNetworkNode getNode2();
    public boolean isBiDirectional();
    public boolean directionReversed();
    public void changeDirection();
    
    public void makeConnection();
}
