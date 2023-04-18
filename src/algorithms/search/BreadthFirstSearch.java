package algorithms.search;

import java.util.*;

/**

 This class implements the Breadth First Search algorithm to solve a given problem
 that implements the ISearchable interface. It extends the ASearchingAlgorithm class.
 Breadth First Search visits all the states at a given depth, before moving to the next level.
 It uses a priority queue to store the unvisited states, and adds the successors of a state to the
 queue only if they haven't been visited yet.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{

    protected PriorityQueue<AState> queue;

    public BreadthFirstSearch(){
        this.name = "Breadth First Search";
        this.countNodeEvaluated = 0;
    }
    @Override
    public Solution solve (ISearchable domain) {
        reset_maze(domain);
        return solveAlgorithm(domain, time_comparator);
    }
    // bfs algorithm
    public Solution solveAlgorithm(ISearchable domain, Comparator<AState> time_comparator) {
        int time = 0;
        PriorityQueue<AState> queue = new PriorityQueue<>(time_comparator);
        queue.add(domain.getStartPosition());
        domain.getStartPosition().setVisited(true);
        domain.getStartPosition().setTime(time);

        while(!queue.isEmpty()) {
            AState temp = queue.poll();
            temp.setVisited(true);
            this.countNodeEvaluated++;

            if (domain.getGoalState().equals(temp)) {
                break;
            }

            else {
                ArrayList<AState> all_neighbors = domain.getAllSuccessors(temp);
                for (AState curr_neighbor : all_neighbors) {
                    if(!queue.contains(curr_neighbor)){
                        curr_neighbor.setParentState(temp);
                        curr_neighbor.setTime(temp.getTime() + 1);
                        queue.add(curr_neighbor);
                    }
                }
            }
        }

        ArrayList<AState> path = getPathOfSolution(domain.getStartPosition(), domain.getGoalState());
        return new Solution(path,this.countNodeEvaluated);
    }

    // comparator for the priority queue - general comparator
    Comparator<AState> time_comparator = new Comparator<AState>() {
        @Override
        public int compare(AState o1, AState o2) {
            int time1 = o1.getTime();
            int time2 = o2.getTime();
            return Integer.compare(time1, time2);
        }
    };
}




