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
package mscproject.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.scene.Node;
import org.json.JSONObject;
import uk.co.corductive.msc.network.connection.AbstractConnectionView;
import uk.co.corductive.msc.network.node.AbstractNodeView;
import uk.co.corductive.msc.ui.MScProject;

/**
 *
 * @author Andy Keavey
 */
public class GraphController extends AbstractGraphController {
    
    public GraphController() {
        super();
        this.view = new GraphView(this);
        this.model = new GraphModel();
    }
    
    @Override
    public boolean saveGraph() {
        JSONObject jGraph = new JSONObject();
        jGraph.put("title", getModel().getName());
        List<Node> list = this.getView().getChildren();
        for (Node n: list) {
            if (n instanceof AbstractNodeView) {
                jGraph.append("nodes", ((AbstractNodeView)n).getController().getModel().getJSONObject());
            } else if (n instanceof AbstractConnectionView) {
                jGraph.append("connections", ((AbstractConnectionView)n).getController().getModel().getJSONObject());
            }    
        }
        try {
            File saveFile = new File(MScProject.getGlobalUser() + "-" + getModel().getName() + ".HANDi");
            System.err.println(saveFile.exists());
            if (!saveFile.exists()) {
                saveFile.createNewFile();
                FileWriter fw = new FileWriter(saveFile);
                fw.write(jGraph.toString(4));
                fw.flush();
                fw.close();
                return true;
            }
            return false;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
}
