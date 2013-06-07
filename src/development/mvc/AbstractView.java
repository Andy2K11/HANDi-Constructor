package development.mvc;

import javafx.scene.Parent;

public abstract class AbstractView extends Parent {
    
    protected AbstractView(AbstractController controller) {
        super();
        
        this.setOnMouseEntered(controller.getOnMouseEnteredHandler());
        this.setOnMouseExited(controller.getOnMouseExitedHandler());
        this.setOnMouseClicked(controller.getOnMouseClickedHandler());
        
        this.setOnDragDetected(controller.getOnDragDetectedHandler());
        this.setOnDragOver(controller.getOnDragOverHandler());
        this.setOnDragDropped(controller.getOnDragDroppedHandler());
        
        this.setOnKeyPressed(controller.getOnKeyPressedHandler());
    }
    
    public abstract AbstractController getController();
}
