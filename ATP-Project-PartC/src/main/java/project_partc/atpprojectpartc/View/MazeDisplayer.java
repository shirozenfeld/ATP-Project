package project_partc.atpprojectpartc.View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas{

    public Maze getMaze() {
        return maze;
    }

    private Maze maze;
    private int characterPositionRow;
    private int characterPositionColumn;
    private int goalcol;
    private int goalrow;
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();

    public void setMaze(Maze maze) {
        this.maze = maze;
        characterPositionRow=maze.getStartPosition().getRowIndex();
        characterPositionColumn=maze.getStartPosition().getColumnIndex();
        goalrow=maze.getGoalPosition().getRowIndex();
        goalcol=maze.getGoalPosition().getColumnIndex();
        redraw();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }


    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.getMazeMatrix().length;
            double cellWidth = canvasWidth / maze.getMazeMatrix()[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                ImageView characterImageView = new ImageView(characterImage);
                characterImageView.setFitWidth(800);
                characterImageView.setFitHeight(800);
                characterImageView.setPreserveRatio(false);
                Image GoalImage = new Image(new FileInputStream(ImageFileNameGoal.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.getMazeMatrix().length; i++) {
                    for (int j = 0; j < maze.getMazeMatrix()[0].length; j++) {
                        if (maze.getMazeMatrix()[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        else if (maze.getMazeMatrix()[i][j] == 2) {
                            gc.setFill(Color.LIGHTYELLOW);
                            //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                            gc.fillRect(j * cellWidth,i * cellHeight, cellWidth,cellHeight);
                        }
                    }
                }

                //Draw Character
                //gc.setFill(Color.RED);
                // gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow* cellHeight, cellWidth , cellHeight);
                gc.drawImage(GoalImage, goalcol * cellWidth,  goalrow * cellHeight, cellWidth , cellHeight);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
public void clear() {
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());
}
    public void resetCellZero(int row, int col){
        maze.getMazeMatrix()[row][col]=0;
        redraw();
    }

    public void setSolve(int row,int col){
        maze.getMazeMatrix()[row][col]=2;
        redraw();
    }


    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public StringProperty imageFileNameWallProperty() {
        return ImageFileNameWall;
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public StringProperty imageFileNameCharacterProperty() {
        return ImageFileNameCharacter;
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public StringProperty imageFileNameGoalProperty() {
        return ImageFileNameGoal;
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }

    //endregion

}
