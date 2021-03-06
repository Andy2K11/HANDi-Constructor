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
package uk.co.corductive.msc.ui;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.corductive.msc.login.MScLogin.Loginable;

/**
 * This is where the main application starts. It implements the Loginable interface
 * which is defined by the preloader. In this way, data from the preloader can
 * be passed to this application on startup.
 * 
 * @author Andy
 */
public class MScProject extends Application implements Loginable {
    
    /**
     * The system property defined by "user.home". 
     */
    public static final String userhome = System.getProperties().getProperty("user.home");
    
    private static final Image ICON_32 = new Image(MScProject.class.getResourceAsStream("/resources/he-icon.png"));    
    private String username = null;
    private Stage stage = null;
    private Locale locale = null;
    private Parent root = null;
    
    private static String globalUser;
    
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
    
    /**
     * Creates and shows the main scene defined by "UI.fxml". The normal close icon is prevented from
     * working so that the File:Exit menu command must be used. This ensures the 
     * saving of any diagrams that have been left open.
     * 
     * This could be handled better by dialog boxes that prompt for saving per 
     * diagram on closing the application.
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        /* prevents closing of appliction using X - must do file close
         * which saves all tabs
         */
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        ResourceBundle bundle = ResourceBundle.getBundle("resources.bundle", locale);
        root =  FXMLLoader.load(getClass().getResource("UI.fxml"), bundle);
        
        Scene scene = new Scene(root);
        stage.setTitle(bundle.getString("title"));
        stage.getIcons().add(ICON_32);
        stage.setScene(scene);
        stage.setX(400);
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
    
    /**
     * Takes the SHA1 generated username from the preloader and sets the first
     * 12 characters as the global variable globalUser
     * 
     * @param username 
     */
    @Override
    public void setUser(String username) {
        this.username = username;
        MScProject.globalUser = username.substring(0, Math.min(12, username.length()));
        showStage();
        System.out.println(username + " passed to application:" + globalUser);
    }
        
    public static String getGlobalUser() {
        return globalUser;
    }
    
    public static String getFilePath() {
        return userhome + File.separator + "msc" + File.separator;
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
