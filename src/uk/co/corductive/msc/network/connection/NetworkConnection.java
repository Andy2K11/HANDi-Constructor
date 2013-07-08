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
    
    public boolean isDirectional();
    public void setDirectional(boolean direction);
    public boolean isDirectionReversed();     // is node2 parent instead of node1
    public void changeDirection();
    public boolean isParent(NetworkNode node);
    
    public void makeConnection();
    public void removeConnection();
    public void removeConnection(NetworkNode node);
}
