package algorithms.mazeGenerators;

import java.util.Arrays;

public class Maze {

    private int columns;
    private int rows;
    private int[][] mazeMatrix;
    private Position startPosition;
    private Position endPosition;

//    public Maze(int rows, int columns, int[][] mazeMatrix) {
    public Maze(int[][] mazeMatrix, Position startPosition, Position endPosition) {
        this.rows = mazeMatrix.length;
        this.columns = mazeMatrix[0].length;
        this.mazeMatrix = mazeMatrix;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public int getMazeCellValue(int row, int column){
        return this.mazeMatrix[row][column];
    }


    @Override
    public String toString() {
        return "Maze{" +
                "maze=" + Arrays.toString(mazeMatrix) +
                '}';
    }
    public void printMaze() {
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                if(i==0 && j==0){
                    System.out.print("S");
                }
                else if(i==rows-1 && j==columns-1){
                    System.out.print("E");
                }
                else{
                    System.out.print(this.mazeMatrix[i][j]);
                }
            }
            System.out.println();
        }
    }
    public Position getStartPosition(){
        return this.startPosition;
    }

    public Position getGoalPosition(){
        return this.endPosition;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
