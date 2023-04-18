package algorithms.mazeGenerators;

import java.util.Arrays;
/**

 A Maze class that represents a maze with its dimensions, cell values, start and end positions.
 */
public class Maze {

    private int columns;
    private int rows;
    private int[][] mazeMatrix;
    private Position startPosition;
    private Position endPosition;
    /**
     * Constructor that initializes a new instance of Maze class with the given parameters.
     */
    public Maze(int[][] mazeMatrix, Position startPosition, Position endPosition) {
        this.rows = mazeMatrix.length;
        this.columns = mazeMatrix[0].length;
        this.mazeMatrix = mazeMatrix;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
    //returns the value in the given cell 0/1
    public int getMazeCellValue(int row, int column){
        return this.mazeMatrix[row][column];
    }

    //Returns a string representation of the Maze object.
    @Override
    public String toString() {
        return "Maze{" +
                "maze=" + Arrays.toString(mazeMatrix) +
                '}';
    }

    /**
     * Prints the Maze object.
     */
    public void print() {
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

    //get functions
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
