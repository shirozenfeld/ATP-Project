package algorithms.mazeGenerators;

import java.util.Arrays;

public class EmptyMazeGenerator extends AMazeGenerator{

    public Maze generate (int rows, int columns){
        int[][] mazeMatrix = new int[rows][columns];
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(rows, columns);

        for (int i = 0; i<rows; i++) {
            Arrays.fill(mazeMatrix[i], 0);
        }
        return new Maze(mazeMatrix,startPosition, endPosition);

    };
}
