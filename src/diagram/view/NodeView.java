package diagram.view;

import diagram.control.NodeController;
import diagram.model.NodeModel;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 *
 * @author Andy
 */
public class NodeView extends Parent {
    static final double radius = 7.0;
    private double x, y;
    private final Circle node = new Circle(0, 0, radius);
    private NodeController nodeController;
    
    public NodeView(double x, double y) {
        this.x = x;
        this.y = y;
        this.getChildren().add(node);
        setLayoutX(x);
        setLayoutY(y);    
    }
    
    public NodeView createController(NodeModel nodeModel) {
        nodeController = new NodeController(this, nodeModel);
        return this;
    }
    
    public NodeController getNodeController() {
        return nodeController;
    }
}
