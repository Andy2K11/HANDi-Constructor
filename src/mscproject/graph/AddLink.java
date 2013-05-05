/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph;

import javafx.scene.shape.LineTo;

/**
 *
 * @author Andy
 */
public class AddLink extends SimpleLink {
    LineTo lt2;
    
    public AddLink(SimpleNode n1, SimpleNode n2) {
        super(n1, n2);
        lt = new LineTo(cx, cy);
        lt2 = new LineTo(n2x, n2y);
        path.getElements().setAll(mt, lt, lt2);
    }
    
    @Override
    public void updateLayout() {
        doLayout();
        mt.setX(n1x);
        mt.setY(n1y);
        lt.setX(cx);
        lt.setY(cy);
        lt2.setX(n2x);
        lt2.setY(n2y);
        control.setCenterX(cx);
        control.setCenterY(cy);
    }
}
