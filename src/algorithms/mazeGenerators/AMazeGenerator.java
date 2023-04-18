package algorithms.mazeGenerators;
import java.lang.*;
// abstract class that provides a template for generating mazes
public abstract class AMazeGenerator implements IMazeGenerator{


    private Maze maze;
    //generate a new maze - abstract function
    public abstract Maze generate (int rows, int columns);

    // Measures the time it takes to generate a maze.
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long startGenerateTime;
        long finishGenerateTime;
        startGenerateTime = System.currentTimeMillis();
        this.maze = generate(rows, columns);
        finishGenerateTime = System.currentTimeMillis();
        return finishGenerateTime - startGenerateTime;
    }

}
