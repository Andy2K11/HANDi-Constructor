/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.corductive.msc.ui;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import loginfxml.MScLogin.Loginable;
//import loginfxml.MScLogin.Loginable;

/**
 *
 * @author Andy
 */
public class MScProject extends Application implements Loginable {

    private static final Image ICON_32 = new Image(MScProject.class.getResourceAsStream("he-icon.png"));    
    private String username = null;
    private Stage stage = null;
    private Locale locale = null;
    private Parent root = null;
    
    static String globalUser;
    
    /**
     * Read in any parameters set.
     * Command line arguments are:
     *  test : sets locale to test locale which hides certain details from the user
     */
    @Override
    public void init() {
        Application.Parameters params = this.getParameters();
        Map<String, String> named = params.getNamed();
        List<String> cmdLine = params.getUnnamed();
        for (String s: cmdLine) {
            if (s.equals("test")) {
                locale = new Locale("en", "GB", "Test");
            }
        }
        // if locale not set then give it a default
        if (locale==null) { locale = Locale.UK; }
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        ResourceBundle bundle = ResourceBundle.getBundle("resources.bundle", locale);
        root =  FXMLLoader.load(getClass().getResource("UI.fxml"), bundle);
        
        Scene scene = new Scene(root);
        stage.setTitle(bundle.getString("title"));
        stage.getIcons().add(ICON_32);
        stage.setScene(scene);
        showStage();
    }

    /*
     * We need both the login details and the stage to finish loading before
     * calling show(), but we don't know which order these tasks will be complete
     * so showStage must be called after both operations complete with checks.
     */
    private void showStage() {
        if (username!=null && stage!=null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                   stage.show();
                   stage.setTitle(stage.getTitle()  + " : " + username);
                } 
            });
            
        }
    }
    
    
    @Override
    public void setUser(String username) {
        this.username = username;
        MScProject.globalUser = username.substring(0, Math.min(8, username.length()));
        showStage();
        System.out.println(username + " passed to application:" + globalUser);
    }
        
    public static String getGlobalUser() {
        return globalUser;
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