package algorithms.search;

import algorithms.mazeGenerators.Position;

public abstract class AState {

    private Object currState;
    private AState parentState;
    private double cost;
    private int time;
    boolean visited;

    @Override
    public String toString() {
        return "(" + ((Position)getCurrState()).getRowIndex() + "," +
                ((Position)getCurrState()).getColumnIndex() + ")";
    }

    public AState(Object currState) {
        this.currState = currState;
        this.parentState = null;
        this.cost = 0;
        this.visited = false;
        this.time = 0;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Object getCurrState() {
        return this.currState;
    }

    public void setCurrState(Object currState) {
        this.currState = currState;
    }

    public AState getParentState() {
        return parentState;
    }

    public void setParentState(AState parentState) {
        this.parentState = parentState;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


}
