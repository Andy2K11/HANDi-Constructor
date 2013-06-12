package development.mvc.network.node;

import development.mvc.AbstractView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AbstractNodeView extends AbstractView {
    
    AbstractNodeController controller;
    
    public static final double DEFAULT_RADIUS = 8.0;
    
    protected AbstractNodeView(AbstractNodeController controller) {
        super(controller);
        this.controller = controller;
        
        controller.getModel().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setLayoutX(newValue.doubleValue());
            }    
        });
        
        controller.getModel().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                setLayoutY(newValue.doubleValue());
            }    
        });
    }
    
    @Override
    public AbstractNodeController getController() {
        return controller;
    }
}
