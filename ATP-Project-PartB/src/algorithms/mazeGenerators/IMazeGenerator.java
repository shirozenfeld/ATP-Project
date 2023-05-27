package algorithms.mazeGenerators;
//interface for generating a maze
public interface IMazeGenerator {
    //function to generate a maze
    Maze generate (int rows, int columns);
    //function for measuring the time of the algorithm
    long measureAlgorithmTimeMillis(int rows, int columns);

}
