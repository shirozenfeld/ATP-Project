package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    private int data;

    public MazeState(Position currState, int data) {
        super(currState);
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Position getPosition () {
        return (Position) this.getCurrState();
    }
}
