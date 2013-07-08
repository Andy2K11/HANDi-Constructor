package uk.co.corductive.msc.network.node;

import uk.co.corductive.msc.mvc.AbstractView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import mscproject.graph.GraphView;

public abstract class AbstractNodeView extends Parent implements AbstractView {
    
    AbstractNodeController controller;
    
    
    
    public static final double DEFAULT_RADIUS = 8.0;
    
    protected AbstractNodeView(AbstractNodeController controller) {
        this.controller = controller;
        
        this.setOnMouseEntered(controller.getOnMouseEnteredHandler());
        this.setOnMouseExited(controller.getOnMouseExitedHandler());
        this.setOnMouseClicked(controller.getOnMouseClickedHandler());
        this.setOnMousePressed(controller.getOnMousePressedHandler());
        this.setOnMouseDragged(controller.getOnMouseDraggedHandler());
        
        this.setOnDragDetected(controller.getOnDragDetectedHandler());
        this.setOnDragOver(controller.getOnDragOverHandler());
        this.setOnDragDropped(controller.getOnDragDroppedHandler());

        this.setOnKeyPressed(controller.getOnKeyPressedHandler());
        
        controller.getModel().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setTranslateX(newValue.doubleValue());
            }    
        });
        
        controller.getModel().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setTranslateY(newValue.doubleValue());
            }    
        });
    }
    
    @Override
    public AbstractNodeController getController() {
        return controller;
    }
    
    public void remove() {
        ((GraphView)this.getParent()).getChildren().remove(this);
    }
}
