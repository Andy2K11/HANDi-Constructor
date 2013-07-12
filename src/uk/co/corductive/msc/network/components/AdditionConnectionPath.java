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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author Andy
 */
public class AdditionConnectionPath extends AbstractConnectionPath {

    private MoveTo start = new MoveTo();
    private LineTo control = new LineTo();
    private LineTo bridge = new LineTo();
    private LineTo end = new LineTo();
    
    public AdditionConnectionPath() {
        super();
        
        this.middleX = new DoubleBinding() {
            {
                super.bind(start.xProperty(), end.xProperty(), control.xProperty(), bridge.xProperty());
            }
            @Override
            protected double computeValue() {
                return control.getX() + (bridge.getX() - control.getX())/2;
            }
        };
        this.middleY = new DoubleBinding() {
            {
                super.bind(start.yProperty(), end.yProperty(), control.yProperty(), bridge.yProperty());
            }
            @Override
            protected double computeValue() {
                return control.getY() + (bridge.getY() - control.getY())/2;
            }
        };
        
        this.getElements().addAll(start, control, bridge, end);
        
        
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
        bridge.setY(y);
    }

    @Override
    public void setControlX(double x) {
        control.setX(x);
        bridge.setX(x);   
    }

    @Override
    public void setControlY(double y) {
        control.setY(y);
    }

    @Override
    public double getControlX() {
        return control.getX();
    }

    @Override
    public double getControlY() {
        return control.getY();
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
        return control.xProperty();
    }

    @Override
    public DoubleProperty doublePropertyControlY() {
        return control.yProperty();
    }
}
