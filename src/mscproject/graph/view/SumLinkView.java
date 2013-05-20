/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.view;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class SumLinkView extends LinkView implements Routable {
    
    private MoveTo start = new MoveTo();
    private LineTo mid = new LineTo();
    private LineTo base = new LineTo();
    private LineTo end = new LineTo();
    
    public SumLinkView() {
        path.getElements().addAll(start, mid, base, end);
    }
  
    @Override
    public void setStartPosition(double x, double y) {
        start.setX(x);
        start.setY(y);
    }

    @Override
    public void setEndPosition(double x, double y) {
        end.setX(x);
        end.setY(y);
    }    
    
    @Override
    public void setControlPosition(double x, double y) {
        mid.setX(x);
        mid.setY(start.getY());
        base.setX(x);
        base.setY(end.getY());
    }

    @Override
    public void calculateControlPosition() {
        setControlPosition(end.getX()-20.0, 0.0);
    }
    
}
