package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    public void testName() throws Exception {
        BestFirstSearch bestFS = new BestFirstSearch();
        String name = bestFS.getName();
        assertEquals("Best First Search", name);
    }


    //all 1 except start - there is no path and only 1 is evaluated
    @Test
    public void testAllWalls() throws Exception{

        int[][] mazeMatrix = new int[10][10];
        for(int i=0;i<10;i++){
            Arrays.fill(mazeMatrix[i], 1);
        }
        mazeMatrix[0][0] = 0;
        Position startPosition = new Position(0,0);
        Position endPosition = new Position(10,10);
        Maze maze = new Maze(mazeMatrix, startPosition, endPosition);
        ISearchable searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(searchableMaze);
        assertEquals(0,solution.getSolutionPath().size());
        assertEquals(1,solution.getNumberOfNodesEvaluation());
    }


    //the size of the maze is 1xm the solution path size will be m
    @Test
    public void testMazeSize1xm () throws Exception {
        Maze maze = (new MyMazeGenerator()).generate(1/*rows*/, 17/*columns*/);
        ISearchable searchableMaze = new SearchableMaze((maze));
        BestFirstSearch bestFS = new BestFirstSearch();
        Solution solution = bestFS.solve(searchableMaze);

        ArrayList<AState> sol_path = solution.getSolutionPath();
        assertEquals(17,sol_path.size());
    }

    //time under 1 minute
    @Test
    public void testSearchTime() throws Exception{
        Maze maze = (new MyMazeGenerator()).generate(1000/*rows*/, 1000/*columns*/);
        long startGenerateTime;
        long finishGenerateTime;

        BestFirstSearch bestFS = new BestFirstSearch();
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        startGenerateTime = System.currentTimeMillis();
        bestFS.solve(searchableMaze);
        finishGenerateTime = System.currentTimeMillis();

        assertTrue( finishGenerateTime - startGenerateTime < 60000);
    }

    @Test
    public void testMazeSize0xm() throws Exception {
        Maze maze = (new MyMazeGenerator()).generate(0/*rows*/, 17/*columns*/);
        boolean check = maze == null;
        assertTrue(check);
    }
}