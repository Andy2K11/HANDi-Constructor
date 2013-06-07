/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package development.mvc.network.node;

import javafx.scene.shape.Circle;

/**
 *
 * @author Andy Keavey
 */
public class NodeView extends AbstractNodeView {
    
    private Circle node = new Circle(0, 0, DEFAULT_RADIUS);
    
    public NodeView(AbstractNodeController controller) {
        super(controller);
        this.getChildren().add(node);
    }
}
