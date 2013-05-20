/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.graph.view;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class LinkView extends AbstractLinkView {

    private MoveTo start = new MoveTo();
    private LineTo end = new LineTo(); 
    
    public LinkView() {
        path.getElements().addAll(start, end);

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
}
