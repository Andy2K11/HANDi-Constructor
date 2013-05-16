package mscproject.graph;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import static mscproject.graph.controller.GraphController.*;
/**
 *
 * @author Andy
 */
public abstract class AbstractLink extends Parent {
    
    SimpleNode node1, node2;
    double n1x, n1y, n2x, n2y;
    Path path = new Path();
    
    public AbstractLink(SimpleNode node1, SimpleNode node2) {
        this.node1 = node1;
        this.node2 = node2;
        path.getStyleClass().add("link");
        this.setOnMouseEntered(mscproject.graph.controller.GraphController.handleMouseEntered);
        this.setOnMouseExited(mscproject.graph.controller.GraphController.handleMouseExited);
        this.setOnMouseClicked(mscproject.graph.controller.LinkController.handleMouseClicked);
        this.setOnKeyPressed(mscproject.graph.controller.LinkController.handleKeyPressed);

        if (this instanceof Shapeable) {
            System.out.println("Shapeable");
            this.setOnDragOver(handleDragOver);
            this.setOnDragDetected(handleDragDetected);
        }
    }

    /**
     * This method is responsible for updating and setting all the layout values
     * required for all the elements instantiated by createLinkView().
     * Implementations should make a call to updateCommonLayoutValues().
     */
    public abstract void updateLayout();
    

    final void updateCommonLayoutValues() {
        n1x = node1.getLayoutX();
        n1y = node1.getLayoutY();
        n2x = node2.getLayoutX();
        n2y = node2.getLayoutY();
    }

    public final AbstractLink add() {
        node1.addLink(this);
        node2.addLink(this);
        
        if (this instanceof Shapeable) {
            ((Shapeable)this).shapeLink();
        }
        updateLayout();       
        this.getChildren().add(path);
        return this;
    }
    
    public final void remove() {
        this.removeEventHandler(MouseEvent.MOUSE_EXITED, mscproject.graph.controller.GraphController.handleMouseExited);
        getNode1().removeLink(this);
        getNode2().removeLink(this);
        ((Graph)this.getParent()).getChildren().remove(this);
    }
    
    public SimpleNode getNode1() {
        return node1;
    }
    
    public SimpleNode getNode2() {
        return node2;
    }
    
     /**
     * Method to fetch the node connected to the other end of this link, the 
     * calling node supplies itself as a parameter. If the supplied node does
     * not match either node connected to this link then a null value is returned.
     * This method is required to iterate through a network.
     * 
     * @param aNode
     * @return 
     */   
    public SimpleNode getLinkedNode(SimpleNode sn) {
        if (sn.equals(node1)) {
            return node2;
        } else if (sn.equals(node2)) {
            return node1;
        } else {
            System.err.println("Neither node in link.");
            return null;
        }
    }
    
    /**
     * Determines if the node supplied as a parameter is a child node. Will 
     * return false if the node is either parent or not connected.
     * 
     * @param sn
     * @return 
     */
    public boolean isSubNode(SimpleNode sn) {
        return (sn.getLayoutY() > this.getLinkedNode(sn).getLayoutY());
    }    
}
