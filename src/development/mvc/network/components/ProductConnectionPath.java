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
package development.mvc.network.components;

import javafx.scene.shape.MoveTo;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy Keavey
 */
public class ProductConnectionPath extends AbstractConnectionPath {

    private MoveTo start = new MoveTo();
    private QuadCurveTo end = new QuadCurveTo();
    private double controlX=0.0, controlY=0.0;
    
    public ProductConnectionPath() {
        super();
        this.getElements().addAll(start, end);
    }
    
    @Override
    public void setStartX(double x) {
        start.setX(x);
    }

    @Override
    public void setStartY(double y) {
        start.setY(y);
        end.setControlY(start.getY() + controlY);
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);
        end.setControlX(end.getX() + controlX);
    }

    @Override
    public void setEndY(double y) {
        end.setY(y);
    }

    @Override
    public void setControlX(double x) {
        end.setControlX(end.getControlX() + x);
    }

    @Override
    public void setControlY(double y) {
        end.setControlY(end.getControlY() + y);
    }

    @Override
    public double getControlX() {
        return end.getControlX();
    }

    @Override
    public double getControlY() {
        return end.getControlY();
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
    public void incrementStartY(double dy) {
        setStartY(getStartY() + dy);
        setControlY(getControlY() + dy);
        //setControlY(getStartY());
    }
    
    @Override
    public void incrementEndX(double dx) {
        setEndX(getEndX() + dx);
        setControlX(getControlX() + dx);
        //setControlX(getEndX());
    }
    
    @Override
    public void updateLayout() {
        /*
         * sc : start to control
         * ce : control to end
         */
        double ratio = 2.0 / 3.0;
        double scX = start.getX() + ((end.getControlX() - start.getX()) * ratio);
        double scY = start.getY() + ((end.getControlY() - start.getY()) * ratio);
        double ceX = end.getControlX() + ((end.getX() - end.getControlX()) * ratio);
        double ceY = end.getControlY() + ((end.getY() - end.getControlY()) * ratio);
        middleX.set( scX + ((ceX - scX) * ratio) );
        middleY.set( scY + ((ceY - scY) * ratio) );
    }
}
