package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public interface ISearchable {
    AState getStartPosition();
    AState getGoalState();
    ArrayList<AState> getAllSuccessors(AState state);
    MazeState[][] getMazeState ();
}
