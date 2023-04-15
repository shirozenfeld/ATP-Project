package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Comparator;
import java.util.PriorityQueue;

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

    private static Comparator<AState> cost_comparator = new Comparator<AState>() {
        @Override
        public int compare(AState state1, AState state2) {
            return (int)state1.getCost() - (int)state2.getCost();
        }

    /*private static Comparator<AState> cost_comparator = new Comparator<AState>() {
        @Override
        public int compare(AState state1, AState state2){
            double cost1 = state1.getCost();
            double cost2 = state2.getCost();
            if(isDiagonalMove(state1, state2)){
                cost1 += 5;
                cost2 += 5;
            }
            return (int)(cost1 - cost2);
        }

        private boolean isDiagonalMove(AState s1, AState s2) {

            int pos_x = Math.abs(((Position)s1.getCurrState()).getRowIndex() - ((Position)s2.getCurrState()).getRowIndex());
            int pos_y = Math.abs(((Position)s1.getCurrState()).getColumnIndex() - ((Position)s2.getCurrState()).getColumnIndex());
            if (pos_x == 1 && pos_y == 1)
                return true;
            return false;

        }*/
    };
}

