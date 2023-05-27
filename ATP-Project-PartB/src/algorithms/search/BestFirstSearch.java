package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Comparator;
import java.util.PriorityQueue;
// best first search algorithm - extends bfs and has the same algorithm but different compare function
public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        this.name = "Best First Search";
        this.countNodeEvaluated = 0;
        this.queue = new PriorityQueue<>(time_comparator);
    }

    @Override
    public Solution solve(ISearchable domain) {
        reset_maze(domain);
        return solveAlgorithm(domain, cost_comparator);
    }

    // compare by the cost of the step
    private static Comparator<AState> cost_comparator = new Comparator<AState>() {
        @Override
        public int compare(AState state1, AState state2) {
            return (int)state1.getCost() - (int)state2.getCost();
        }

    };
}

