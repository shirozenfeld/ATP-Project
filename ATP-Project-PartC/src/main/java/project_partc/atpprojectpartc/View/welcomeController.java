package project_partc.atpprojectpartc.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project_partc.atpprojectpartc.Model.MyModel;
import project_partc.atpprojectpartc.ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.Optional;

public class welcomeController {
    private final Logger LOG = LogManager.getLogger();

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
    MyModel model = new MyModel();
    public Button playButton;


    public void start(ActionEvent actionEvent) throws Exception {
        Stage primaryStage = new Stage();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("My Application!");
        //FXMLLoader fxmlLoader = new FXMLLoader();
        //Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());

        File fxmlFile = new File("src/main/java/project_partc/atpprojectpartc/View/MyView.fxml");

        //fxmlLoader.setLocation(fxmlFile.toURI().toURL());
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());

        Parent root = fxmlLoader.load();


        Scene scene = new Scene(root, 800, 700);
        //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());

        // Create a File object representing the CSS file
        File cssFile = new File("src/main/java/project_partc/atpprojectpartc/View/ViewStyle.css");
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());



        primaryStage.setMinWidth(635);
        primaryStage.setMinHeight(390);
        primaryStage.setScene(scene);
        //--------------
        MyViewController myViewController = fxmlLoader.getController();
        myViewController.setResizeEvent(scene);
        myViewController.setZoom(scene);
        myViewController.playMusic();
        myViewController.setViewModel(viewModel);
        viewModel.addObserver(myViewController);
        myViewController.disBTN();
        //--------------
        SetStageCloseEvent(primaryStage);
        primaryStage.show();


        Stage stage2 = (Stage) playButton.getScene().getWindow();
        stage2.close();

    }


    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    model.stop();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });

    }
}
