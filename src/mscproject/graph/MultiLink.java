package mscproject.graph;

import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy
 */
public class MultiLink extends AbstractLink implements Shapeable, Negatable {
    MoveTo mt = new MoveTo();
    QuadCurveTo ct = new QuadCurveTo();
    double cx, cy, mx, my, userX = 0.0;
    Circle control;
    
    Path negate = new Path();
    boolean negated = true;
    
    public MultiLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        path.getElements().addAll(mt, ct);
        negate = NegatableFactory.createNegationView(NegatableFactory.NegationType.TWO);
        this.getChildren().add(negate);
        negate.setVisible(negated);
        
        //control point for curve
        control = new Circle(cx, cy, 3);
        control.setVisible(true);
        this.getChildren().add(control); 
    }
    
    @Override
    public void shapeLink() {
        updateCommonLayoutValues();
        double controlYFactor = 2 * Math.pow(1.01, Math.abs(n2x - n1x));
        double weight = (150 / controlYFactor) - 10;       //more vertical alignment means greater weight, the -10 helps zero out the term at greater distances
        if (n2x - n1x < 0) {
            weight = -weight;
        }
        if (n1y < n2y) {
            cx = n2x + weight;
            cy = n1y + (n2y - n1y)/controlYFactor;
        } else {
            cx = n1x + weight;
            cy = n2y + (n1y - n2y)/controlYFactor;
        }       
    }
    
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        /* find a nice position for the control point such that when nodes
         * are vertically aligned the horizontal forms a line of symetry, but
         * when nodes are horizontal distal an aesthetically arching curve is
         * formed from a high control point.
         */
        mt.setX(n1x);
        mt.setY(n1y);
        ct.setControlX(cx+userX);
        ct.setControlY(cy);
        ct.setX(n2x);
        ct.setY(n2y);
        control.setCenterX(cx+userX);
        control.setCenterY(cy);
        
        
        //calculate midpoint of quad curve
        //find mid point of P0 to P1 (first point to control)
        double p01X = n1x + (cx - n1x)/2;
        double p01Y = n1y + (cy - n1y)/2;
        //now mid points for P1 to P2 (control point to second point)
        double p12X = cx + (n2x - cx)/2;
        double p12Y = cy + (n2y - cy)/2;
        //now the mid point of the line formed between the first tow mid points
        mx = p01X + (p12X - p01X)/2;
        my = p01Y + (p12Y - p01Y)/2;
        negate.setLayoutX(mx);
        negate.setLayoutY(my);
    }
    
    @Override
    public void setControlPoint(double x) {
        userX = x;
    }
    
    @Override
    public double getUserX() {
        return userX;
    }
    
    public void setControlPoint(double x, double y) {
        cx = x;
        cy = y;
    }
     
    public void toggleSelected() {
        control.setVisible(!control.isVisible());
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
