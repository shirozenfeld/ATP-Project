package algorithms.search;

import java.util.ArrayList;
// class to save the solution of a search algorithm
public class Solution {

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
