/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.factory;

import uk.co.corductive.msc.network.components.AbstractConnectionPath;
import uk.co.corductive.msc.network.components.AdditionConnectionPath;
import uk.co.corductive.msc.network.components.EqualityConnectionPath;
import uk.co.corductive.msc.network.components.ProductConnectionPath;
import uk.co.corductive.msc.network.components.UnityConnectionPath;
import uk.co.corductive.msc.network.connection.Operator.Operation;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public class HandiComponentFactory extends AbstractComponentFactory {
    
    
    
    
    @Override
    public AbstractConnectionPath getConnectionPath(Operation operator) {
        AbstractConnectionPath path;
        switch(operator) {
            case ADDITION: path = new AdditionConnectionPath();
                break;
            case MULTIPLICATION: path = new ProductConnectionPath();
                break;
            case EQUALITY: path = new EqualityConnectionPath();
                break;
            default:
            case NONE: path = new UnityConnectionPath();
                break;
        }
        return path;
    }
    
    public Path getNegationPath(Operation operator) {
        Path negation;
        switch(operator) {
            case NONE:
            case ADDITION: negation = aNegation();
                break;
            case MULTIPLICATION: negation = bNegation();
                break;
            default:
            case EQUALITY: negation = new Path();   // empty path
                break;
        }
        return negation;
    }
    
    /* move this stuff to new classes */
    
    private Path aNegation() {
        Path path = new Path();
        MoveTo mt = new MoveTo(-15.0, 0.0);
        LineTo lt = new LineTo(15.0, 0.0);
        path.getElements().addAll(mt, lt);
        return path;
    }
    
    private Path bNegation() {
        Path path = new Path();
        MoveTo mt = new MoveTo(-15.0, 3.0);
        LineTo lt = new LineTo(15.0, -3.0);
        path.getElements().addAll(mt, lt);
        return path;
    }
}
