package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    AState getStartPosition();
    AState getGoalState();
    ArrayList<AState> getAllSuccessors(AState state);
    MazeState[][] getMazeState ();
}
