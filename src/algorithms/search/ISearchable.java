package algorithms.search;

import java.util.ArrayList;
// creating a not searchable object to searchable by a search algorithm
public interface ISearchable {
    AState getStartPosition();
    AState getGoalState();
    ArrayList<AState> getAllSuccessors(AState state);
    MazeState[][] getMazeState ();
}
