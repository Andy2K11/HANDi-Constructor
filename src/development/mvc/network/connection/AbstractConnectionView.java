package development.mvc.network.connection;

import development.mvc.AbstractView;
import development.mvc.network.components.AbstractConnectionPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Path;

public abstract class AbstractConnectionView extends AbstractView {
    
    AbstractConnectionPath path;
    AbstractConnectionController controller;
    Path negate;
    
    protected AbstractConnectionView(AbstractConnectionController controller) {
        super(controller);
        this.controller = controller;

        AbstractConnectionModel model = controller.getModel();
        model.getNode1().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setStartX(newValue.doubleValue());
                getPath().incrementStartX(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode1().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setStartY(newValue.doubleValue());
                getPath().incrementStartY(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode2().doublePropertyX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setEndX(newValue.doubleValue());
                getPath().incrementEndX(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });
        
        model.getNode2().doublePropertyY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                //getPath().setEndY(newValue.doubleValue());
                getPath().incrementEndY(newValue.doubleValue() - oldValue.doubleValue());
                //getPath().updateLayout();
            }    
        });  
    }
        
    public AbstractConnectionPath getPath() {
        return path;
    }
    
    public Path getNegate() {
        return negate;
    }
        
    public void createPath(AbstractConnectionPath path) {
        this.path = path;
        this.getChildren().add(path);
        path.toBack();
        path.updateLayout();    // initialize middle x, y position
        path.getStyleClass().add("link");
        // temp test line -- move to factory
        //createNegate(new Circle(path.getMiddleX(), path.getMiddleY(), 5.0));
    }
    
    public void createNegate(Path negate) {
        this.negate = negate;
        negate.setLayoutX(getPath().middleX().doubleValue());
        getPath().middleX().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                getNegate().setLayoutX(newValue.doubleValue());
            }    
        });
        negate.setLayoutY(getPath().middleY().doubleValue());
        getPath().middleY().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
                getNegate().setLayoutY(newValue.doubleValue());
            }    
        });
        negate.setVisible(controller.getModel().isNegated());
        controller.getModel().negateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
                getNegate().setVisible(newValue);
            }
        });
        this.getChildren().add(negate);
        this.negate.toBack();
        negate.getStyleClass().add("link");
    }
    
    @Override
    public AbstractConnectionController getController() {
        return controller;
    }
}
