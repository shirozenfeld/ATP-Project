package algorithms.mazeGenerators;
import java.lang.*;

public abstract class AMazeGenerator implements IMazeGenerator{


    private Maze maze;

    public abstract Maze generate (int rows, int columns);

    public long measureAlgorithmTimeMillis(int rows, int columns){
        long startGenerateTime;
        long finishGenerateTime;
        startGenerateTime = System.currentTimeMillis();
        this.maze = generate(rows, columns);
        finishGenerateTime = System.currentTimeMillis();
        return finishGenerateTime - startGenerateTime;
    }

}
