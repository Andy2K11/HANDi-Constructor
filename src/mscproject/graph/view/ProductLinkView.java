/**
 * Defines whether a link can have its path altered by the user
 */
package mscproject.graph.view;

import javafx.scene.shape.MoveTo;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy
 */
public class ProductLinkView extends AbstractLinkView implements Routable {
    
    private MoveTo start = new MoveTo();
    private QuadCurveTo end = new QuadCurveTo();   
    
    public ProductLinkView() {
        path.getElements().addAll(start, end);
    }
    
    @Override
    public final void setStartPosition(double x, double y) {
        start.setX(x);
        start.setY(y);
    }

    @Override
    public final void setEndPosition(double x, double y) {
        end.setX(x);
        end.setY(y);
    }

    @Override
    public final void setControlPosition(double x, double y) {
        end.setControlX(x);
        end.setControlY(y);
    }
    
    @Override
    public void calculateControlPosition() {
        setControlPosition(end.getX(), start.getY());
    }
   
}
