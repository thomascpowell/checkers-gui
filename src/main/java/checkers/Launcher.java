package checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Seth Nans, Gabriel Strickland, Thomas Powell, Sabella Malisher, Zachary McMillan
 * Date: 4/21/2025
 * Section: CSC 331
 * Program Purpose: Launch the program.
 */


public class Launcher extends Application {
    /**
     * Loads the FXML and sets the scene.
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("checkers.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point to the program.
     * */
    public static void main(String[] args) {
        launch(args);
    }
}
