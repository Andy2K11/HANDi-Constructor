/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import java.util.TreeSet;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;


/**
 *
 * @author Andy
 */
public class DropLink extends AbstractLink {
    double cx, cy;
    final double controlRatioY = 4.0;
    MoveTo mt, nmt;
    LineTo lt, nlt;
    
    Path negate = new Path();
    
    boolean negated = false;
    
    public DropLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
    }
    
    /**
     * creates all the shapes and elements required to display this object.
     * Needs to be called only once when the add() method is called as part 
     * of the factory design pattern. 
     * Must be implemented by all subclasses of link.
     */
    @Override
    void createLinkView() {
        //the link
        mt = new MoveTo(n1x, n1y);
        lt = new LineTo(n2x, n2y); 
        path.getElements().setAll(mt, lt);
        
        //negation dash
        nmt = new MoveTo(mx -5, my);
        nlt = new LineTo(mx +5, my);
        negate.getElements().setAll(nmt, nlt);
        negate.setVisible(negated);
        
        //add shapes to Link object
        this.getChildren().addAll(path, negate);
    }

    
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(n2x);
        lt.setY(n2y);
    }
    
    @Override
    public DropLink add() {
        node1.addLink(this);
        node2.addLink(this);
        createLinkView();
        updateLayout();
        return this;
    }
    


    
    public void toggleNegated() {
        negated = !negated;
        negate.setVisible(negated);
    }
    
   
}
