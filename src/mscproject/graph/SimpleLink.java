package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class SimpleLink extends AbstractLink {
    private MoveTo mt;
    private LineTo lt;
    
    public SimpleLink(SimpleNode node1, SimpleNode node2) {
        super(node1, node2);
    }
    
    @Override
    void createLinkView() {
        //the link
        mt = new MoveTo(n1x, n1y);
        lt = new LineTo(n2x, n2y); 
        path.getElements().addAll(mt, lt);
    }
    
    /**
     * What is provided here is a default which can be useful for 
     * simple applications.
     */
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(n2x);
        lt.setY(n2y);
    }
}
