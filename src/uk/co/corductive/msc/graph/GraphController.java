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
package uk.co.corductive.msc.graph;

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
        /* get the JSON model data for the graph as a whole */
        JSONObject jGraph = getModel().getJSONObject();
        jGraph.put("actions", getModel().recordAction("save", getModel().getJSONObject()));
        
        /* now go through all the nodes and connections for that graph and 
         * add their JSON representations */
        List<Node> list = getView().getChildren();
        for (Node n: list) {
            if (n instanceof AbstractNodeView) {
                jGraph.append("nodes", ((AbstractNodeView)n).getController().getModel().getJSONObject());
            } else if (n instanceof AbstractConnectionView) {
                jGraph.append("connections", ((AbstractConnectionView)n).getController().getModel().getJSONObject());
            }    
        }
        try {
            String path = MScProject.getFilePath() + MScProject.getGlobalUser();
            File pathFile = new File(path);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            
            String file = path + File.separator + MScProject.getGlobalUser() + "." + getModel().getName() + ".HANDi";
            File saveFile = new File(file);
            
            /* if the file already exists then append the fileNum to the name, then check again,
             * incrementing the number each time an existing file is found until a unique name.
             */
            int fileNum = 2;
            while (saveFile.exists()) {
                saveFile = new File(file + fileNum);
                fileNum++;
            }
            saveFile.createNewFile();
            
            FileWriter fw = new FileWriter(saveFile);
            fw.write(jGraph.toString(4));
            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
}
