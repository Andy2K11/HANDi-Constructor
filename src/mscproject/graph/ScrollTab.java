/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

/**
 *
 * @author Andy
 */
public class ScrollTab extends Tab {
    private ScrollPane scrollPane;
    private Graph graph;
    private static int tabNum = 0;
    private TextField nameField;
    
    public ScrollTab(final String name) {
        super();
        tabNum++;
        scrollPane = new ScrollPane();
        graph = new Graph(name);
        scrollPane.setContent(graph);
        this.setContent(scrollPane);
        
        nameField = new TextField(name);
        this.setGraphic(nameField);
        nameField.setText(name);
        nameField.setPrefColumnCount(name.length());

        nameField.setDisable(true);
     
        nameField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graph.setId(nameField.getText());
                nameField.setPrefColumnCount(name.length());
                System.out.println("Diagram name changed");
                nameField.setDisable(true);
                event.consume();
            }
        });
        
        nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                if (!newValue.booleanValue()) {
                    nameField.fireEvent(new ActionEvent());
                }
            }
        });
        
        graph.nameProperty().bind(nameField.textProperty());
        //nameField.textProperty().bind(graph.nameProperty());
        
        /* nameField.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            @Override
            public void handle(InputMethodEvent event) {
                System.out.println("Changing name");
            }
        }); */
    }
    
    public void setName(String name) {
        nameField.setText(name);
    }
    
    public ScrollTab() {
        this("diagram_" + (tabNum+1));
    }

    public Graph getGraph() {
        return graph;
    }

}
