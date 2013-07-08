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

package uk.co.corductive.msc.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import uk.co.corductive.msc.graph.AbstractGraphView;

/**
 *
 * @author Andy
 */
public class ScrollTab extends Tab {
    private TextField nameField;
    private ScrollPane scrollPane;
    private static int tabNum = 0;
    private final Tab thisTab = this;
    
    public ScrollTab() {
        this("diagram_" + (tabNum+1));
    }

    public ScrollTab(final String name) {
        super();
        tabNum++;
        
        scrollPane = new ScrollPane();
        this.setContent(scrollPane);
        
        nameField = new TextField(name);
        this.setGraphic(nameField);
        nameField.setText(name);
        nameField.setPrefColumnCount(name.length());
        nameField.setDisable(true);

        /*
         * When the user hits enter after editing, the text field is disabled.
         */
        nameField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameField.setPrefColumnCount(name.length());
                nameField.setDisable(true);
                event.consume();
            }
        });
        
        /*
         * If the focus is lost, then fire an action event. This means clicking
         * away from the text field will have the same effect as hitting enter.
         * * * isFocused becomes false!
         */
        nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                if (!newValue.booleanValue()) {
                    nameField.fireEvent(new ActionEvent());
                }
            }
        });
        nameField.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                event.getTransferMode();
                event.consume();
                getTabPane().getSelectionModel().select(thisTab);
            }
        });
        
        nameField.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                event.getTransferMode();
                //getTabPane().getSelectionModel().select((Tab)event.getGestureTarget());
            }
        });
        
        this.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // do stuff
            }
        });
        
    }
    
    /**
     * As setContent is a final method, use this method instead to add a Node to 
     * a ScrollTab.
     * 
     * @param view 
     */
    public void setGraph(AbstractGraphView view) {
        scrollPane.setContent(view);
    }
    
    public AbstractGraphView getGraph() {
        return (AbstractGraphView) scrollPane.getContent();
    }
    
    public void setName(String name) {
        nameField.setText(name);
    }
}
