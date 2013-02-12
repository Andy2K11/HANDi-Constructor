/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Andy
 */
public class MScProject extends Application {
    
    private static final Image ICON_32 = new Image(MScProject.class.getResourceAsStream("he-icon.png"));    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MScProjectView.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("University of Sussex : Evaluation Environment");
        stage.getIcons().add(ICON_32);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
