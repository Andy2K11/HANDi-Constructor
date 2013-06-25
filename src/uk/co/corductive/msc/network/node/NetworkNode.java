/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.node;

import java.util.Set;
import uk.co.corductive.msc.network.connection.NetworkConnection;

/**
 *
 * @author Andy
 */
public interface NetworkNode {
    public void addConnection(NetworkConnection connection);
    public void removeConnection(NetworkConnection connection);
    public Set<NetworkNode> getSubNodeTree();
    //Set<NetworkNode> buildTree(Set<NetworkNode> tree);
}
