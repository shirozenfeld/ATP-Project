package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    
    public Maze generate (int rows, int columns){
        Position startPosition = new Position(0,0);
        Position endPosition = new Position(rows,columns);
        int[][] mazeMatrix = new int[rows][columns];

        for(int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if(Math.random()>0.5){
                    mazeMatrix[i][j] = 1;
                }
                else{
                    mazeMatrix[i][j] = 0;
                }
                if(i==0 || j == rows - 1){
                    mazeMatrix[i][j] = 0;
                }
            }
        }
        return new Maze(mazeMatrix, startPosition, endPosition);
    }
}


