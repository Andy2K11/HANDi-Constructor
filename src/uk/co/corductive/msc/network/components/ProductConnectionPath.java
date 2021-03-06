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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.QuadCurveTo;

/**
 *
 * @author Andy Keavey
 */
public class ProductConnectionPath extends AbstractConnectionPath {

    private MoveTo start = new MoveTo();
    private QuadCurveTo end = new QuadCurveTo();
    
    public ProductConnectionPath() {
        super();
        
        final double ratio = (2.0/3.0);
        this.middleX = new DoubleBinding() {
            {
                super.bind(start.xProperty(), end.controlXProperty(), end.xProperty());
            }
            @Override
            protected double computeValue() {
                double scX = start.getX() + ((end.getControlX() - start.getX()) * ratio);
                double ceX = end.getControlX() + ((end.getX() - end.getControlX()) * ratio);
                return scX + ((ceX - scX) * ratio);
            }
        };
        this.middleY = new DoubleBinding() {
            {
                super.bind(start.yProperty(), end.controlYProperty(), end.yProperty());
            }
            @Override
            protected double computeValue() {
                double scY = start.getY() + ((end.getControlY() - start.getY()) * ratio);
                double ceY = end.getControlY() + ((end.getY() - end.getControlY()) * ratio);
                return scY + ((ceY - scY) * ratio);
            }
        };
        this.getElements().addAll(start, end);

        // can not use binding because must be able to manually adjust path - can not set bound property!
        start.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setControlY(getControlY() + (newValue.doubleValue() - oldValue.doubleValue()));
            }    
        });
        
        end.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setControlX(getControlX() + (newValue.doubleValue() - oldValue.doubleValue()));
            }    
        });

        /*
        start.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setControlY(getControlY() + (newValue.doubleValue() - oldValue.doubleValue()));
            }    
        });
        
        end.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setControlX(getControlX() + (newValue.doubleValue() - oldValue.doubleValue()));
            }    
        });
        */
    }
    
    @Override
    public void setStartX(double x) {
        start.setX(x);
    }

    @Override
    public void setStartY(double y) {
        start.setY(y);    
    }

    @Override
    public void setEndX(double x) {
        end.setX(x);    
    }

    @Override
    public void setEndY(double y) {
        end.setY(y);
    }

    @Override
    public void setControlX(double x) {
        end.setControlX(x);
    }

    @Override
    public void setControlY(double y) {
        end.setControlY(y);
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
    public DoubleProperty doublePropertyControlX() {
        return end.controlXProperty();
    }

    @Override
    public DoubleProperty doublePropertyControlY() {
        return end.controlYProperty();
    }
}
