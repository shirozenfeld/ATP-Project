package algorithms.search;
import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> stack;

    public DepthFirstSearch() {
        this.name = "Depth First Search";
        this.countNodeEvaluated = 0;
    }

    @Override
    public Solution solve(ISearchable domain) {
        reset_maze(domain);
        Stack<AState> stack = new Stack<>();
        stack.push(domain.getStartPosition());
        domain.getStartPosition().setVisited(true);

        while(!stack.empty()){
            AState temp = stack.pop();
            temp.setVisited(true);
            this.countNodeEvaluated++;

            if (domain.getGoalState().equals(temp)) {
                break;
            }
            else {
                ArrayList<AState> all_neighbors = domain.getAllSuccessors(temp);
                for (AState curr_neighbor : all_neighbors) {
                    if(!stack.contains(curr_neighbor)){
                        curr_neighbor.setParentState(temp);
                        stack.add(curr_neighbor);
                    }
                }
            }
        }

        ArrayList<AState> path = getPathOfSolution(domain.getStartPosition(), domain.getGoalState());
        return new Solution(path,this.countNodeEvaluated);

    }
}