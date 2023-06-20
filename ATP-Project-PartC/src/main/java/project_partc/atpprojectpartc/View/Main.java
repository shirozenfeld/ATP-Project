package project_partc.atpprojectpartc.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello!");
        //Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));

        // Create a File object representing the absolute file path of the FXML file
        File file = new File("src/main/java/project_partc/atpprojectpartc/View/welcome.fxml");

        // Load the FXML file from the File object
        FXMLLoader fxmlLoader = new FXMLLoader(file.toURI().toURL());

        // Retrieve the root node of the loaded FXML file
        Parent root = fxmlLoader.load();


        Scene scene = new Scene(root, 600, 360);
//        scene.getStylesheets().add(getClass().getResource("welcome.css").toExternalForm());

        // Create a File object representing the absolute file path of the CSS file
        File cssFile = new File("src/main/java/project_partc/atpprojectpartc/View/welcome.css");
        // Add the CSS file to the Scene's stylesheets
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());


        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxWidth(600);
        primaryStage.setMaxHeight(400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
