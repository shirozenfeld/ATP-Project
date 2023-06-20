package project_partc.atpprojectpartc.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project_partc.atpprojectpartc.Model.MyModel;
import project_partc.atpprojectpartc.ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements Observer, IView {

    @FXML
    private MyViewModel viewModel;
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.TextField txtfld_rowsNum;
    public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button solveBTN;
    public javafx.scene.control.Button removeBTN;
    public BorderPane borderPain123;
    private MediaPlayer mediaPlayer;
    public StringProperty CharacterRow = new SimpleStringProperty();

    public StringProperty CharacterColumn = new SimpleStringProperty();

    private final Logger LOG = LogManager.getLogger();


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }
    private static int index=0;
    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            btn_generateMaze.setDisable(false);
            if(((MyModel)viewModel.getModel()).getS().getSolutionPath().size()>0)
                setSol();
        }
    }

    public void stopM(){
        mediaPlayer.stop();
    }

    public void playMu(){
        Double volume = 0.1;
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    @Override
    public void displayMaze(Maze maze) {
        mazeDisplayer.setMaze(maze);
        //mazeDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->mazeDisplayer.requestFocus());
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CharacterRow.set(characterPositionRow + "");
                CharacterColumn.set(characterPositionColumn + "");
            }
        });

        if((mazeDisplayer.getCharacterPositionRow()==mazeDisplayer.getMaze().getGoalPosition().getRowIndex()) && (mazeDisplayer.getCharacterPositionColumn()==mazeDisplayer.getMaze().getGoalPosition().getColumnIndex())){
            mediaPlayer.stop();
            win();
            removeSol();
            mazeDisplayer.clear();
            disBTN();
        }
    }

    public void win() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Congratulations!");
            FXMLLoader fxmlLoader = new FXMLLoader();


            File fxmlFile = new File("src/main/java/project_partc/atpprojectpartc/View/win.fxml");
            fxmlLoader.setLocation(fxmlFile.toURI().toURL());
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 340, 340);

            File cssFile = new File("src/main/java/project_partc/atpprojectpartc/View/Win.css");
            scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());


            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();

            String path = "win.mp3";
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            Double volume = 0.1;
            mediaPlayer.setVolume(volume);
            mediaPlayer.setAutoPlay(true);
            MediaView mediaView = new MediaView(mediaPlayer);

            mazeDisplayer.clear();
        } catch (Exception e) {

        }
    }
    public void disBTN(){
        solveBTN.setDisable(true);
        removeBTN.setDisable(true);
    }

    public void generateMaze() {
        int heigth = 0;
        int width = 0;
        try {
            heigth = Integer.valueOf(txtfld_rowsNum.getText());
            width = Integer.valueOf(txtfld_columnsNum.getText());
            if(heigth <= 0 || width <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter positive integer values for height and width.");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException e) {
            // Display an alert if the values are not valid integers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid integer values for height and width.");
            alert.showAndWait();
        }
        btn_generateMaze.setDisable(true);
        viewModel.generateMaze(width, heigth);
        solveBTN.setDisable(false);
        removeBTN.setDisable(true);
        mediaPlayer.stop();
        Double volume = 0.1;
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solve();
        solveBTN.setDisable(true);
        removeBTN.setDisable(false);
        //((MyModel)viewModel.getModel()).setS(new Solution());
    }
    public void removeSol(){
        ArrayList mazeSolutionSteps = ((MyModel)viewModel.getModel()).getS().getSolutionPath();

        for(int i = 0; i < mazeSolutionSteps.size(); i++) {
            mazeDisplayer.resetCellZero(((MazeState)mazeSolutionSteps.get(i)).getPosition().getRowIndex(),((MazeState)mazeSolutionSteps.get(i)).getPosition().getColumnIndex());
        }
        //((MyModel)viewModel.getModel()).setS(new Solution());
        solveBTN.setDisable(false);
        removeBTN.setDisable(true);

    }

    private void setSol(){

        ArrayList mazeSolutionSteps = ((MyModel)viewModel.getModel()).getS().getSolutionPath();

        for(int i = 0; i < mazeSolutionSteps.size(); i++) {
            mazeDisplayer.setSolve(((MazeState)mazeSolutionSteps.get(i)).getPosition().getRowIndex(),((MazeState)mazeSolutionSteps.get(i)).getPosition().getColumnIndex());
        }
    }
    public void setZoom(final Scene s){
        s.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.isControlDown()) {
                    double zoomi = 1.05;
                    double deltha = event.getDeltaY();
                    if (deltha < 0)
                        zoomi = 2.0 - zoomi;
                    borderPain123.setScaleX(borderPain123.getScaleX() * zoomi);
                    borderPain123.setScaleY(borderPain123.getScaleY() * zoomi);
                    event.consume();
                }
            }
        });
    }

    public void mouseDrag(MouseEvent ms){
        if(mazeDisplayer.getMaze()==null)
            return;
        else{
            int x=(int) ((ms.getX())/(mazeDisplayer.getWidth()/ viewModel.getMaze().getMazeMatrix()[0].length));
            int y=(int) ((ms.getY())/(mazeDisplayer.getWidth()/ viewModel.getMaze().getMazeMatrix().length));
            if(y<viewModel.getCharacterPositionRow())
                viewModel.moveCharacter(KeyCode.UP);
            if(y>viewModel.getCharacterPositionRow())
                viewModel.moveCharacter(KeyCode.DOWN);
            if(x<viewModel.getCharacterPositionColumn())
                viewModel.moveCharacter(KeyCode.LEFT);
            if(x>viewModel.getCharacterPositionColumn())
                viewModel.moveCharacter(KeyCode.RIGHT);

            mazeDisplayer.requestFocus();
        }
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
    public  static  void sendToShow(String S){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(S);
        alert.show();
    }
    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }


    public String getCharacterRow() {
        return CharacterRow.get();
    }

    public StringProperty characterRowProperty() {
        return CharacterRow;
    }

    public String getCharacterColumn() {
        return CharacterColumn.get();
    }

    public StringProperty characterColumnProperty() {
        return CharacterColumn;
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //System.out.println("Width: " + newSceneWidth);
                mazeDisplayer.setWidth(newSceneWidth.doubleValue()*0.65);
               mazeDisplayer.redraw();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                //System.out.println("Height: " + newSceneHeight);
                mazeDisplayer.setHeight(newSceneHeight.doubleValue()*0.8);
               mazeDisplayer.redraw();
            }
        });
      //  mazeDisplayer.redraw();
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            //Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());

            File fxmlFile = new File("src/main/java/project_partc/atpprojectpartc/View/About.fxml");
            fxmlLoader.setLocation(fxmlFile.toURI().toURL());
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 670, 330);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }


    public void Properties(ActionEvent actionEvent){
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            //Parent root = fxmlLoader.load(getClass().getResource("prop.fxml").openStream());

            File fxmlFile = new File("src/main/java/project_partc/atpprojectpartc/View/prop.fxml");
            fxmlLoader.setLocation(fxmlFile.toURI().toURL());
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 300, 200);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        }catch (Exception e){}
    }

    public void help(ActionEvent actionEvent){
        try {
            Stage stage = new Stage();
            stage.setTitle("Help");
            FXMLLoader fxmlLoader = new FXMLLoader();
            //Parent root = fxmlLoader.load(getClass().getResource("help.fxml").openStream());

            File fxmlFile = new File("src/main/java/project_partc/atpprojectpartc/View/help.fxml");
            fxmlLoader.setLocation(fxmlFile.toURI().toURL());
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 450, 350);
            //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        }catch (Exception e){}
    }


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void playMusic() {
        LOG.info("Music started");
        String path = "resources/Wishing_Girl.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
    }

    public void save (ActionEvent actionEvent)throws IOException{
        FileChooser choose = new FileChooser();
        choose.setTitle("Save file");
        choose.setInitialFileName("Maze"+index);
        index++;
        choose.setInitialDirectory(new File("./AllMaze"));
        File maze=choose.showSaveDialog((Stage)btn_generateMaze.getScene().getWindow());
        viewModel.save(maze);
        }

    public void open (){ viewModel.open();}


    public void stop(){
        viewModel.stop();
    }

}
