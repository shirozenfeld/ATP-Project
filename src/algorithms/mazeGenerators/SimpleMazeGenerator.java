package algorithms.mazeGenerators;
import java.util.Random;
// a maze that promises there is a passage from start to end
public class SimpleMazeGenerator extends AMazeGenerator{
    // the function to create the maze
    public Maze generate (int rows, int columns){
        Position startPosition = new Position(0,0);
        Position endPosition = new Position(rows,columns);
        int[][] mazeMatrix = new int[rows][columns];
        // create a maze that has a passage
        for(int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                //choose randomly if the cell value will be 1 or 0
                if(Math.random()>0.5){
                    mazeMatrix[i][j] = 1;
                }
                else{
                    mazeMatrix[i][j] = 0;
                }
                //keep one row and column 0 so there is a passage from start to end
                if(i==0 || j == rows - 1){
                    mazeMatrix[i][j] = 0;
                }
            }
        }
        return new Maze(mazeMatrix, startPosition, endPosition);
    }
}


