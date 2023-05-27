package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
// class to save the solution of a search algorithm
public class Solution implements Serializable {

    private ArrayList<AState> solutionPath;
    private int numberOfNodesEvaluation;

    public Solution(ArrayList<AState> solutionPath, int numberOfNodesEvaluation) {
        this.solutionPath = solutionPath;
        this.numberOfNodesEvaluation = numberOfNodesEvaluation;
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    public int getNumberOfNodesEvaluation() {
        return numberOfNodesEvaluation;
    }

}
