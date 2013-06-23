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
package uk.co.corductive.msc.network.components;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy Keavey
 */
public class UnityConnectionPath extends AbstractConnectionPath {

    MoveTo start = new MoveTo();
    LineTo end = new LineTo();
    

    
    public UnityConnectionPath() {
        super();
        this.middleX = new DoubleBinding() {
            {
                super.bind(start.xProperty(), end.xProperty());
            }
            @Override
            protected double computeValue() {
                return start.getX() + (end.getX() - start.getX())/2;
            }
        };
        this.middleY = new DoubleBinding() {
            {
                super.bind(start.yProperty(), end.yProperty());
            }
            @Override
            protected double computeValue() {
                return start.getY() + (end.getY() - start.getY())/2;
            }
        };
        this.getElements().addAll(start, end);
    }
    
    @Override
    public void setStartX(double x) {
        start.setX(x);
        //middleX.invalidate();
    }

    @Override
    public void setStartY(double y) {
        start.setY(y);
        //middleY.invalidate();
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);
        //middleX.invalidate();
    }

    @Override
    public void setEndY(double y) {
        end.setY(y);
        //middleY.invalidate();
    }

    @Override
    public void setControlX(double x) {
        // do nothing
    }

    @Override
    public void setControlY(double y) {
        // do nothing
    }

    @Override
    public double getStartX() {
        return start.getX();
    }

    @Override
    public double getStartY() {
        return start.getY();
    }

    @Override
    public double getEndX() {
        return end.getX();
    }

    @Override
    public double getEndY() {
        return end.getY();
    }
    
    @Override
    public double getControlX() {
        return -Double.MAX_VALUE;
    }

    @Override
    public double getControlY() {
        return -Double.MAX_VALUE;
    }  

    @Override
    public void updateLayout() {
      //  middleX.set( start.getX() + (end.getX() - start.getX())/2 );
      //  middleY.set( start.getY() + (end.getY() - start.getY())/2 );
    }   
}
