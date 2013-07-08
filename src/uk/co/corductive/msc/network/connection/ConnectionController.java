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

import uk.co.corductive.msc.network.connection.Operator.Operation;
import uk.co.corductive.msc.network.node.AbstractNodeController;

/**
 *
 * @author Andy Keavey
 */
public class ConnectionController extends AbstractConnectionController {
    
    public ConnectionController(AbstractNodeController node1, AbstractNodeController node2, Operation operator) {
        super(node1, node2);
        this.model = new ConnectionModel(node1.getModel(), node2.getModel(), operator);
        this.view = new ConnectionView(this);
    }

}
