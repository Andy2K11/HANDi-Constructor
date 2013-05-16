/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mscproject.ui;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import loginfxml.MScLogin.Loginable;

/**
 *
 * @author Andy
 */
public class MScProject extends Application implements Loginable {

    private static final Image ICON_32 = new Image(MScProject.class.getResourceAsStream("he-icon.png"));    
    String username = null;
    Stage stage = null;
    Locale locale = null;
    Parent root = null;
    
    /*
     * We need both the login details and the stage to finish loading before
     * calling show(), but we don't know which order these tasks will be complete
     * so showStage must be called after both operations complete with checks.
     */
    private void showStage() {
        if (username!=null && stage!=null) {
            stage.show();
        }
    }
    
    @Override
    public void setUser(String username, Locale locale) {
        this.username = username; 
        this.locale = locale;
        showStage();
        System.out.println(username);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //Locale britishEnglish = Locale.UK;
        //Locale locale = new Locale("en", "GB", "Test");
        ResourceBundle bundle = ResourceBundle.getBundle("resources.bundle", Locale.UK);
        root =  FXMLLoader.load(getClass().getResource("MScProjectView.fxml"), bundle);
        
        Scene scene = new Scene(root);
        stage.setTitle(bundle.getString("title"));
        stage.getIcons().add(ICON_32);
        stage.setScene(scene);
        showStage();   
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
