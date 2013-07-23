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
package uk.co.corductive.msc.network.node;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import uk.co.corductive.msc.factory.NetworkFactory;
import uk.co.corductive.msc.graph.AbstractGraphView;
import uk.co.corductive.msc.ui.ToolBarController;
import static uk.co.corductive.msc.ui.ToolBarController.Tool.delete;

/**
 *
 * @author Andy Keavey
 */
public class NodeController extends AbstractNodeController {
    
    public NodeController(NetworkFactory factory) {
        super(factory);
        this.model = new NodeModel();
        this.view = new NodeView(this);
    }
    
    @Override
    public EventHandler<MouseEvent> getOnMouseClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isAltDown()) {
                    ((NodeView)getView()).toggleValue();
                } else if (event.isControlDown()) {
                    ((NodeView)getView()).toggleName();
                } else {
                    switch(ToolBarController.getSelectedTool()) {
                        case delete: 
                        ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("delete-node", getModel().getJSONObject());
                        remove();
                            break;
                        default:
                            ((AbstractGraphView)getView().getParent()).getController().getModel().recordAction("complex", getModel().getJSONObject());
                            if (event.isStillSincePress()) ((NodeModel)getModel()).incrementComplex();
                            break;
                    }
                }
                event.consume();
            }
        };
    }
}
