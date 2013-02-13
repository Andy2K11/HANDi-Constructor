/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class EqualLink extends SimpleLink {
    MoveTo mt1, mt2;
    LineTo lt1, lt2;
    
    public EqualLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        mt1 = new MoveTo(n1x, n1y+3);
        mt2 = new MoveTo(n1x, n1y-3);
        lt1 = new LineTo(n2x, n2y+3);
        lt2 = new LineTo(n2x, n2y-3);
        path.getElements().setAll(mt1, lt1, mt2, lt2);
    }
    
    @Override
    void updateLayout() {
        doLayout();
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
