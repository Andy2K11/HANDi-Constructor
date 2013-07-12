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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy Keavey
 */
public class EqualityConnectionPath extends AbstractConnectionPath {

    MoveTo start = new MoveTo();
    LineTo end = new LineTo();
    MoveTo start2 = new MoveTo();
    LineTo end2 = new LineTo();
    
    private static final double OFFSET = 3.0;
    public EqualityConnectionPath() {
        super();
        this.middleX = new DoubleBinding() {
            @Override
            protected double computeValue() {
                return start.getX() + (end.getX() - start.getX())/2;
            }
        };
        this.middleY = new DoubleBinding() {
            @Override
            protected double computeValue() {
                return start.getY() + (end.getY() - start.getY())/2;
            }
        };
        this.getElements().addAll(start, end, start2, end2);
    }
    
    @Override
    public void setStartX(double x) {
        start.setX(x);
        start2.setX(x);
    }

    @Override
    public void setStartY(double y) {
        start.setY(y - OFFSET);
        start2.setY(y + OFFSET);
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);
        end2.setX(x);
    }

    @Override
    public void setEndY(double y) {
        end.setY(y - OFFSET);
        end2.setY(y + OFFSET);
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
        return start.getY() + OFFSET;
    }

    @Override
    public double getEndX() {
        return end.getX();
    }

    @Override
    public double getEndY() {
        return end.getY() + OFFSET;
    }
    
    @Override
    public double getControlX() {
        return -Double.MAX_VALUE;
    }

    @Override
    public double getControlY() {
        return -Double.MAX_VALUE;
    }
}
