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
public class EqualLinkView extends LinkView {

    private MoveTo start = new MoveTo();
    private LineTo end = new LineTo();
    private MoveTo start2 = new MoveTo();
    private LineTo end2 = new LineTo();
    
    private final double offset = 3.0;
    
    public EqualLinkView() {
        path.getElements().addAll(start, end, start2, end2);
    }
  
    @Override
    public void setStartPosition(double x, double y) {
        start.setX(x);
        start.setY(y - offset);
        start2.setX(x);
        start2.setY(y + offset);
    }

    @Override
    public void setEndPosition(double x, double y) {
        end.setX(x);
        end.setY(y - offset);
        end2.setX(x);
        end2.setY(y + offset);
    }    
}
