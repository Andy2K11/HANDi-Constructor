package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class EqualLink extends SingleLink {
    MoveTo mt1 = new MoveTo(), mt2 = new MoveTo();
    LineTo lt1 = new LineTo(), lt2 = new LineTo();
    
    public EqualLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        path.getElements().setAll(mt1, lt1, mt2, lt2);
    }
    
    @Override
    public void updateLayout() {
        updateCommonLayoutValues();
        mt1.setX(n1x);
        mt1.setY(n1y+3);
        mt2.setX(n1x);
        mt2.setY(n1y-3);
        lt1.setX(n2x);
        lt1.setY(n2y+3);
        lt2.setX(n2x);
        lt2.setY(n2y-3);
    }
}
