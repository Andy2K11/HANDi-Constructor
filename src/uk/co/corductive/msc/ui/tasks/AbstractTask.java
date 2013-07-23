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
package uk.co.corductive.msc.ui.tasks;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.corductive.msc.ui.MScProject;

/**
 *
 * @author Andy Keavey
 */
public class AbstractTask extends Stage {
    // @FXML GridPane grid;
    
    private VBox root;
    private GridPane grid;
    ResourceBundle bundle, menubundle;
    private static final Image ICON_32 = new Image(MScProject.class.getResourceAsStream("/resources/he-icon.png"));
    
    public AbstractTask(int taskNum) {
        this.setWidth(400);
        this.setHeight(800);
        this.setX(0);
        this.getIcons().add(ICON_32);
        
        root = new VBox();
        root.getStylesheets().add("resources/default.css");
        setScene(new Scene(root));
        
        grid = new GridPane();
        root.getChildren().add(grid);
        grid.getStyleClass().add("grid");
        grid.setVgap(10);
        
        
        menubundle = ResourceBundle.getBundle("resources.bundle");
        String titleString = menubundle.getString("menubar.tasks.task" + taskNum);
        Label title = new Label(titleString);
        title.getStyleClass().add("title");
        this.setTitle(titleString);
        int gridRow = 0; 
        grid.add(title, 0, gridRow++);
        
        bundle = ResourceBundle.getBundle("resources.task"+taskNum);
        //Enumeration keys = bundle.getKeys();
        
        /* Title goes in row 0, first step in row 1 - this is just coincidence,
         * may want to put more preamble in at later date so have gridNum and 
         * stepNum as separate integers.
         */
        int stepNum = 1; // first step number to show
        while (bundle.containsKey("step"+stepNum)) {
            String value = bundle.getString("step"+stepNum);
            // String value = bundle.getString((String) keys.nextElement());
            TextArea text = new TextArea("Step " + stepNum + ": " + value);
            grid.add(text, 0, gridRow);
            //text.setDisable(true);
            text.setEditable(false);
            text.setWrapText(true);
            
            gridRow++;
            stepNum++;
        }
        
        final ImageView iv = new ImageView();
        String url = "resources/he-icon.png";
        Image image = new Image(url);
        iv.setImage(image);
        
        Hyperlink reveal = new Hyperlink("reveal");
        grid.add(reveal, 0, gridRow++);
        final int j = gridRow++;
        reveal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!grid.getChildren().contains(iv)) grid.add(iv, 0, j);
            }
        });
        
        Button close = new Button("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                grid.getScene().getWindow().hide();
            }
        });
        grid.add(close, 0, gridRow);
    }
}
