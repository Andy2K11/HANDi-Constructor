package development.mvc.network.node;

import development.mvc.AbstractView;

public abstract class AbstractNodeView extends AbstractView {
    
    AbstractNodeController controller;
    
    public static final double DEFAULT_RADIUS = 8.0;
    
    protected AbstractNodeView(AbstractNodeController controller) {
        super(controller);
        this.controller = controller;
    }
    
    @Override
    public AbstractNodeController getController() {
        return controller;
    }
}
