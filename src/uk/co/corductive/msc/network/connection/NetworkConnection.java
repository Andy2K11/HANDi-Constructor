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
    public NetworkNode getOtherNode(NetworkNode node);
    
    public boolean isDirectional();
    public void setDirectional(boolean direction);
    public boolean isDirectionReversed();     // is node2 parent instead of node1
    public void changeDirection();
    public boolean isParent(NetworkNode node);
    
    public void makeConnection();
    public void removeConnection();
    public void removeConnection(NetworkNode node);
}
