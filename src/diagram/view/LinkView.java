/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diagram.view;

import diagram.DiagramView;
import diagram.control.LinkController;
import javafx.scene.Parent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;



/**
 *
 * @author Andy
 */
public class LinkView extends Path {
    private LinkController linkController;
    private MoveTo mt = new MoveTo();
    private LineTo lt = new LineTo();
    private Path path = new Path();
    
    public LinkView(double x, double y, double x2, double y2) {
        mt.setX(x);
        mt.setY(y);
        lt.setX(x2);
        lt.setY(y2);
        this.getElements().addAll(mt, lt);
    }
    
    public LinkView createController() {
        linkController = new LinkController();
        return this;
    }
    
    public LinkController getLinkController() {
        return linkController;
    }
}
