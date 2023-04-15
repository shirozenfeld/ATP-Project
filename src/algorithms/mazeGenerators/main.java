package algorithms.mazeGenerators;

public class main {
    public static void main(String[] args) {

        SimpleMazeGenerator newMaze = new SimpleMazeGenerator();
        //newMaze.generate(10,10);
        long diff = newMaze.measureAlgorithmTimeMillis(10,10);
        newMaze.getMaze().printMaze();
    }
}


































