package algorithms.search;
import java.util.*;

/**

 DepthFirstSearch is a searching algorithm that traverses the graph or tree in a depth-first order.
 It uses a Stack data structure to keep track of visited nodes and their children.
 The algorithm starts at the root node (start position), explores as far as possible along each branch before backtracking.
 The algorithm continues until the goal state is found or all possible paths have been explored.
 This implementation extends the ASearchingAlgorithm abstract class.
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

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