/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.network.node;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

/**
 *
 * @author Andy Keavey
 */
public class NodeView extends AbstractNodeView {
    
    private Circle node = new Circle(0, 0, DEFAULT_RADIUS);
    TextField value = new TextField();
    Label name = new Label();
    
    public NodeView(AbstractNodeController controller) {
        super(controller);
        this.getChildren().add(node);
        this.getChildren().add(value);
        //this.getChildren().add(name);
        value.setLayoutX(10.0);
        value.setLayoutY(-30.0);
        value.setPrefColumnCount(8);
        value.textProperty().bindBidirectional(getController().getModel().value);
        value.setVisible(false);
        name.setLayoutX(-70.0);
        name.setLayoutY(-30.0);
        name.setText(getController().getModel().getName());
    }
    
    public void toggleValue() {
        value.setVisible(!value.isVisible());
    }
}
