/*
 * Copyright (C) 2013 Andy Keavey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    
    /**
     * Determines if this connection has a parent - child relationship or if
     * both connected nodes are considered to be on an equal level. 
     * 
     * @return whether or not this connection is hierarchical.
     */
    public boolean isDirectional();
    public void setDirectional(boolean direction);
    /**
     * Nominally, node1 is defined to be the parent node. If this method returns 
     * true then node2 has been defined as parent.
     * 
     * @return true if node2 is parent, false if node1 is parent
     */
    public boolean isDirectionReversed();     // is node2 parent instead of node1
    public void changeDirection();
    
    /**
     * Used to discover if the node supplied as an argument is the parent node.
     * 
     * @param node the node to be confirmed as parent.
     * @return true if node is parent, false in all other conditions.
     */
    public boolean isParent(NetworkNode node);
    
    public void makeConnection();
    public void removeConnection();
    public void removeConnection(NetworkNode node);
}
