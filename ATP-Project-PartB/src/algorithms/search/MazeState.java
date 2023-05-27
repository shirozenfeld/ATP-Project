package algorithms.search;

import algorithms.mazeGenerators.Position;
// a state of a maze
public class MazeState extends AState{

    private int data;
    // saves also the data - the value of the cell
    public MazeState(Position currState, int data) {
        super(currState);
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public Position getPosition () {
        return (Position) this.getCurrState();
    }
}
