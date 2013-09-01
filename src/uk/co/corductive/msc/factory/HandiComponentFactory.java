/*
 * Copyright (C) 2013 Andy Keavey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * Creates the paths which are the visual elements for connections and negation marks.
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
