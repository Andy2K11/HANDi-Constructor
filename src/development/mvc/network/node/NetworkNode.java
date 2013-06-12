/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.node;

import development.mvc.network.connection.NetworkConnection;

/**
 *
 * @author Andy
 */
public interface NetworkNode {
    public void addConnection(NetworkConnection connection);
    public void removeConnection(NetworkConnection connection);
}
