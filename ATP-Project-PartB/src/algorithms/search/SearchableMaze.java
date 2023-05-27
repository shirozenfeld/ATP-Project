package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{
    private Maze maze;
    private final MazeState startState;
    private final MazeState endState;
    protected MazeState[][] newMazeState;
    // creating a maze that can be searched by a search algorithm
    // by creating a new matrix with a MazeState object in each cell according to the given maze
    public SearchableMaze(Maze maze){
        newMazeState = new MazeState[maze.getRows()][maze.getColumns()];
        for(int i=0; i<maze.getRows();i++){
            for(int j=0;j< maze.getColumns();j++){
                newMazeState[i][j] = new MazeState(new Position(i,j), maze.getMazeCellValue(i,j));
            }
        }
        this.startState = newMazeState[0][0];
        this.endState = newMazeState[maze.getRows()-1][maze.getColumns()-1];
        this.maze = maze;
    }

    @Override
    public MazeState[][] getMazeState () {return this.newMazeState;}

    @Override
    public AState getStartPosition() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.endState;
    }

    // a function that gets a certain state and returns it's neighbors that can be visited
    @Override
    public ArrayList<AState> getAllSuccessors(AState state) {

        ArrayList<AState> all_possible_states = new ArrayList<>();
        MazeState curr_state = (MazeState)(state);
        Position curr_position = curr_state.getPosition();

        //if the cell is a wall, there is no way to go
        if (curr_state.getData() == 0) {

            try {
                //down right
                if (newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() + 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() + 1].isVisited() &&
                        (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].getData() == 0 ||
                                newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].getData() == 0))
                {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() + 1]);
                    newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() + 1].setCost(15);
                }

            }
            catch (Exception e)
            {
                System.out.print("");
            }

            try {
                //move right
                if (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].isVisited()) {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1]);
                    if(newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].getCost() != 15) {
                        newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].setCost(10);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }


            try {
                //move down
                if (newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].isVisited()) {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()]);
                    if(newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].getCost() != 15) {
                        newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].setCost(10);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }


            try {
                //down left
                if (newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() - 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() - 1].isVisited() &&
                        (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].getData() == 0 ||
                                newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex()].getData() == 0))
                {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() + - 1]);
                    newMazeState[curr_position.getRowIndex() + 1][curr_position.getColumnIndex() - 1].setCost(15);
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }

            try {
                //up right
                if (newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() + 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() + 1].isVisited() &&
                        (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() + 1].getData() == 0 ||
                                newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].getData() == 0))
                {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() + 1]);
                    newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() + 1].setCost(15);
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }


            try {
                //up
                if (newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].isVisited()) {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()]);
                    if(newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].getCost() != 15) {
                        newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].setCost(10);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }

            try {
                //left
                if (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].isVisited()) {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1]);
                    if(newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].getCost() != 15) {
                        newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].setCost(10);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }


            try {
                //up left
                if (newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() - 1].getData() == 0 &&
                        !newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() - 1].isVisited() &&
                        (newMazeState[curr_position.getRowIndex()][curr_position.getColumnIndex() - 1].getData() == 0 ||
                                newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex()].getData() == 0))
                {
                    all_possible_states.add(newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() - 1]);
                    newMazeState[curr_position.getRowIndex() - 1][curr_position.getColumnIndex() - 1].setCost(15);
                }
            }
            catch (Exception e)
            {
                System.out.print("");
            }
        }
        return all_possible_states;
    }
}
