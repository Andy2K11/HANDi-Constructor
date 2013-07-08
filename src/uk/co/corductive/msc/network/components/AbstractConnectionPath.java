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
import javafx.scene.shape.Path;

/**
 *
 * @author Andy
 */
public abstract class AbstractConnectionPath extends Path {
    
    DoubleBinding middleX;
    DoubleBinding middleY;
    
    public abstract void setStartX(double x);
    public abstract void setStartY(double y);
    public abstract void setEndX(double x);
    public abstract void setEndY(double y);
    public abstract void setControlX(double x);
    public abstract void setControlY(double y);
    
    public abstract double getStartX();
    public abstract double getStartY();
    public abstract double getEndX();
    public abstract double getEndY();
    public abstract double getControlX();
    public abstract double getControlY();
    
    public abstract void updateLayout();
    
    //public abstract void incrementStartX(double dx);
    //public abstract void incrementStartY(double dy);
    //public abstract void incrementEndX(double dx);
    //public abstract void incrementEndY(double dy);

    /* Provide some default behaviours for the incrementations. Some paths
     * will override in order to change control positions too.
     */
    public void incrementStartX(double dx) {
        setStartX(getStartX() + dx);
    }

    public void incrementStartY(double dy) {
        setStartY(getStartY() + dy);
    }

    public void incrementEndX(double dx) {
        setEndX(getEndX() + dx);
    }

    public void incrementEndY(double dy) {
        setEndY(getEndY() + dy);
    }   
    
    public void incrementControlX(double x) {
        setControlX(getControlX() + x);
    }
    
    public void incrementControlY(double y) {
        setControlY(getControlY() + y);
    }
    
    public double getMiddleX() {
        return middleX.doubleValue();
    }

    public double getMiddleY() {
        return middleY.doubleValue();
    }

    public DoubleBinding middleX() {
        return middleX;
    }

    public DoubleBinding middleY() {
        return middleY;
    }
}
