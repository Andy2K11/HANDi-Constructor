package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import mscproject.graph.NegatableFactory.NegationType;


/**
 *
 * @author Andy
 */
public class DropLink extends SingleLink implements Negatable {
    double mx, my;
    //final double controlRatioY = 4.0;
    MoveTo mt, nmt;
    LineTo lt, nlt;
    
    Path negate = new Path();   
    boolean negated = true;
    
    public DropLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        negate = NegatableFactory.createNegationView(NegationType.ONE);
        this.getChildren().add(negate);
        negate.setVisible(negated); 
    }
    
    @Override
    public void updateLayout() {
        super.updateLayout();
        mx = n1x + (n2x - n1x)/2;
        my = n1y + (n2y - n1y)/2;
        negate.setLayoutX(mx);
        negate.setLayoutY(my);
    }
    
    @Override
    public boolean isNegated() {
        return negated;
    }
   
    @Override
    public void negate() {
        negated = !negated;
        negate.setVisible(negated);
    }
}
