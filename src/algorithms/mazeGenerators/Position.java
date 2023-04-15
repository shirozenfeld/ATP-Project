package algorithms.mazeGenerators;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + getRowIndex() + "," + getColumnIndex() + "]";
    }

    public int getRowIndex() {
        return x;
    }

    public int getColumnIndex() {
        return y;
    }
}
