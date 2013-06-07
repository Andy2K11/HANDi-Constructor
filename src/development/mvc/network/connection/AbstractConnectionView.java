package development.mvc.network.connection;

import development.mvc.AbstractView;
import development.mvc.network.components.AbstractConnectionPath;

public abstract class AbstractConnectionView extends AbstractView {
    
    AbstractConnectionPath path;
    AbstractConnectionController controller;
    
    protected AbstractConnectionView(AbstractConnectionController controller) {
        super(controller);
        this.controller = controller;
    }
    
    public void createPath(AbstractConnectionPath path) {
        this.path = path;
    }
    
    @Override
    public AbstractConnectionController getController() {
        return controller;
    }
}
