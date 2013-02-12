package diagram.model;

import diagram.DiagramModel;
import diagram.view.NodeView;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Andy
 */
public class NodeModel {
    private List<LinkModel> links = new LinkedList();
    private double value = 0.0;
    private String expressionString = "?";
    private NodeView nodeView;
    private DiagramModel diagram;
    
    public NodeModel() {
        super();
    }
    
    public NodeModel add(DiagramModel diagram) {
        this.diagram = diagram;
        diagram.getNodes().add(this);
        return this;
    }
    
    public NodeModel createView(double x, double y) {
        nodeView = new NodeView(x, y).createController(this);
        return this;
    }
    
    public NodeView getNodeView() {
        return this.nodeView;
    }
}
