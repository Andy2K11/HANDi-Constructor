package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class AddLink extends AbstractLink {
    MoveTo mt = new MoveTo();
    LineTo lt = new LineTo(), lt2 = new LineTo(), lt3 = new LineTo();
    double cx, cy;
    
    public AddLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        path.getElements().setAll(mt, lt, lt2, lt3);
    }
    
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        cx = n2x;
        cy = n1y;
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(cx);
        lt.setY(cy);
        lt2.setX(cx);
        lt2.setY(n2y);
        lt3.setX(n2x);
        lt3.setY(n2y);
        //control.setCenterX(cx);
        //control.setCenterY(cy);
    }
}
