package algorithms.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
// abstract class for searching algorithms
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
        protected String name;
        protected int countNodeEvaluated;

    @Override
    public String getName() {
        return name;
    }

    public int getNumberOfNodesEvaluated() {
        return countNodeEvaluated;
    }

    // function that gets the start state and end state that are from a maze that has been searched
    // by a search algorithm and returns the path it found
    public ArrayList<AState> getPathOfSolution(AState start, AState end){
        ArrayList<AState> solutionPath = new ArrayList<>();
        AState temp = end;
        int check = 0;
        // loop to go through all the states by going to the parent
        while(temp != null && !temp.equals(start)){
            solutionPath.add(temp);
            temp = temp.getParentState();
            if(temp == null){
                check = 1;
            }
        }
        if(check == 1){
            return new ArrayList<AState>();
        }
        solutionPath.add(start);
        Collections.reverse(solutionPath);
        return solutionPath;
    }

    // function to reset the maze state's details so it can be searched again
    public void reset_maze (ISearchable domain) {
        for (int i = 0; i<domain.getMazeState().length; i++) {
            for (int j=0; j<domain.getMazeState()[0].length; j++) {
                MazeState curr_state = domain.getMazeState()[i][j];

                curr_state.setParentState(null);
                curr_state.setCost(0);
                curr_state.setVisited(false);
                curr_state.setTime(0);
            }
        }
    }
}
