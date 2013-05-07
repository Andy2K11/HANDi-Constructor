package mscproject.graph;

import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy
 */
public class MultiLink extends AbstractLink {
    MoveTo mt;
    QuadCurveTo ct;
    double cx, cy;
    Circle control;
    
    public MultiLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);    
    }
    
    @Override
    void createLinkView() {
        mt = new MoveTo(n1x, n1y);
        ct = new QuadCurveTo(cx, cy, n2x, n2y);
        path.getElements().addAll(mt, ct);
        
        //control point for curve
        control = new Circle(cx, cy, 3);
        control.setVisible(false);
        this.getChildren().add(control);
    }
    
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        double controlYFactor = 2 * Math.pow(1.01, Math.abs(n2x - n1x));
        if (n1y < n2y) {
            cx = n2x;
            cy = n1y + (n2y - n1y)/controlYFactor;
        } else {
            cx = n1x;
            cy = n2y + (n1y - n2y)/controlYFactor;
        }
        mt.setX(n1x);
        mt.setY(n1y);
        ct.setControlX(cx);
        ct.setControlY(cy);
        ct.setX(n2x);
        ct.setY(n2y);
        control.setCenterX(cx);
        control.setCenterY(cy);
    }
    
    public void setControlPoint(double x) {
        cx = x - (n1x - x);
    }
    
    public void setControlPoint(double x, double y) {
        cx = x;
        cy = y;
    }
     
    public void toggleSelected() {
        control.setVisible(!control.isVisible());
    }
}
