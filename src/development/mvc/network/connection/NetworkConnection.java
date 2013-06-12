/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.connection;

import development.mvc.network.node.NetworkNode;

/**
 *
 * @author Andy
 */
public interface NetworkConnection {
    public NetworkNode getNode1();
    public NetworkNode getNode2();
    public boolean isBiDirectional();
    public boolean directionReversed();
    public void changeDirection();
    
    public void makeConnection();
}
