/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy
 */
public class MultiLink extends SimpleLink {
    QuadCurveTo ct;
    public MultiLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        ct = new QuadCurveTo(cx, cy, n2x, n2y);
        path.getElements().setAll(mt, ct);
    }
    
    @Override
    void updateLayout() {
        doLayout();
        mt.setX(n1x);
        mt.setY(n1y);
        ct.setControlX(cx);
        ct.setControlY(cy);
        ct.setX(n2x);
        ct.setY(n2y);
        control.setCenterX(cx);
        control.setCenterY(cy);
    }
    
    @Override
    void setControlPoint(double x) {
        cx = x - (n1x - x);
    }
}
