package algorithms.mazeGenerators;

public class Position {
    private int x;
    private int y;
    /**
     * Constructs a new Position object with the given row and column indices.
     *
     * @param x the row index.
     * @param y the column index.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + getRowIndex() + "," + getColumnIndex() + "]";
    }
    // get functions
    public int getRowIndex() {
        return x;
    }

    public int getColumnIndex() {
        return y;
    }
}
