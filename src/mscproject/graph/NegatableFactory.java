package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class NegatableFactory {
    
    public static enum NegationType {ONE, TWO};
    
    private NegatableFactory() {
        
    }
    
    protected static Path createNegationView(NegationType type) {
        double y;
        switch(type) {
            default:;
            case ONE: y=0;
                break;
            case TWO: y=3;
                break;
        }
        MoveTo nmt = new MoveTo(-10, y);
        LineTo nlt = new LineTo(10, -y);
        Path negate = new Path();
        negate.getElements().addAll(nmt, nlt);
        return negate;
    }
}
