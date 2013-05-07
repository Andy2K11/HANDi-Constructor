package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class AddLink extends AbstractLink {
    MoveTo mt;
    LineTo lt, lt2;
    double cx, cy;
    
    public AddLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);    
    }
    
    @Override
    void createLinkView() {
        mt = new MoveTo(n1x, n1y);
        lt = new LineTo(cx, cy);
        lt2 = new LineTo(n2x, n2y);
        path.getElements().setAll(mt, lt, lt2);
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
        lt2.setX(n2x);
        lt2.setY(n2y);
        //control.setCenterX(cx);
        //control.setCenterY(cy);
    }
}
