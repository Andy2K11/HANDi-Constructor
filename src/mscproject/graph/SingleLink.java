package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class SingleLink extends AbstractLink {
    private MoveTo mt = new MoveTo();
    private LineTo lt = new LineTo();
    
    public SingleLink(SimpleNode node1, SimpleNode node2) {
        super(node1, node2);
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
    
    /**
     * A type of SingleLink is considered equal if they connect the same nodes
     * in either direction. (No concept of direction is intended).
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SingleLink) {
            SingleLink link = (SingleLink) o;
            return ( ((this.node1 == link.node1) && (this.node2 == link.node2))
                    || ((this.node1 == link.node2) && (this.node2 == link.node1)) );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
