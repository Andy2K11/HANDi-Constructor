/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.connection;

import uk.co.corductive.msc.network.node.NetworkNode;

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
    public void removeConnection();
    public void removeConnection(NetworkNode node);
}
