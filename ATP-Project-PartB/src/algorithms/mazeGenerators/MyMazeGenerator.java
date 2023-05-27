package algorithms.mazeGenerators;
import java.util.*;
import java.util.Random;
/**
 * MyMazeGenerator is creating a random maze using the Prim's algorithm. The algorithm works as follows:
 *
 * 1. Start with a grid of walls.
 * 2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
 * 3. While there are walls in the list:
 *    1. Pick a random wall from the list. If the cell on the opposite side isn't in the maze yet, create a passage
 *       between the current cell and the neighboring cell, and mark the neighboring cell as part of the maze. Add the
 *       neighboring cell's walls to the wall list.
 *    2. Remove the wall from the list.
 * 4. The maze is complete when all cells have been visited.
 *
 * This implementation initializes the maze with all walls, then marks the starting cell as part of the maze and adds
 * its neighboring walls to a list. While the list is not empty, it picks a random wall from the list and checks if it
 * has only one neighboring cell that is already part of the maze. If so, it creates a passage between the two cells
 * and adds the neighboring walls of the new cell to the list. It repeats this process until there are no more walls
 * left to check. Finally, it marks the last cell as part of the maze and returns the resulting maze as a Maze object.
 */
public class MyMazeGenerator extends AMazeGenerator{

    public Maze generate (int rows, int columns){

        if (rows == 0 || columns == 0)
            return null;

        ArrayList<Position> neighborList = new ArrayList<>();
        int [][] mazeMatrix = new int[rows][columns];

        for(int i=0;i<rows;i++) {
            Arrays.fill(mazeMatrix[i], 1);
        }
        mazeMatrix[0][0] = 0;
        Position startPosition = new Position(0,0);
        Position endPosition = new Position(rows, columns);

        //add to the neighbors list, the neighbors of the start point

        if (mazeMatrix.length > 1)
            neighborList.add(new Position(1,0));

        if (mazeMatrix[0].length > 1)
             neighborList.add(new Position(0,1));


        //while the neighbor list is not empty, choose a random position and check if only one of it's neighbors is a passage
        while(!neighborList.isEmpty()){
            Random rand = new Random();
            int randomIndex = rand.nextInt(neighborList.size());
            Position new_pos = neighborList.get(randomIndex);
            //count how many neighbors are passages
            int counterPassages = 0;

            //same column, row below
            try{
                if(mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same column, row above

            try{
                if(mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same row, column above
            try{
                if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex() + 1] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }

            //same row, column below
            try{
                if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()-1] == 0){
                    counterPassages++;
                }
            }
            catch (Exception e){
                System.out.print("");
            }
            if(counterPassages==1){
                mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()] = 0;
                //enter all the walls neighbors of new_pos to the neighbors list if they don't exist there
                try{
                    if(mazeMatrix[new_pos.getRowIndex()-1][new_pos.getColumnIndex()] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex()-1, new_pos.getColumnIndex());
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same column, row above

                try{
                    if(mazeMatrix[new_pos.getRowIndex()+1][new_pos.getColumnIndex()] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex()+1, new_pos.getColumnIndex());
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same row, column above
                try{
                    if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex() + 1] == 1){
                        neighborList = addNeighborsToList(neighborList, new_pos.getRowIndex(), new_pos.getColumnIndex()+1);
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }

                //same row, column below
                try{
                    if(mazeMatrix[new_pos.getRowIndex()][new_pos.getColumnIndex()-1] == 1){
                        neighborList = addNeighborsToList(neighborList,  new_pos.getRowIndex(), new_pos.getColumnIndex()-1);
                    }
                }
                catch (Exception e){
                    System.out.print("");
                }
            }
            neighborList.remove(randomIndex);
        }
        mazeMatrix[rows-1][columns-1]=0;
        try{
            mazeMatrix[rows-2][columns-1] = 0;
        }
        catch (Exception e){
            System.out.print("");
        }
        try{
            mazeMatrix[rows-1][columns-2] = 0;
        }
        catch (Exception e){
            System.out.print("");
        }

        return new Maze(mazeMatrix, startPosition,endPosition);
    }
    //function to check if a neighbor (a position) already exists in the neighbor list
    public ArrayList<Position> addNeighborsToList(ArrayList<Position> neighborList, int x, int y){
        if(neighborList.isEmpty()){
            neighborList.add(new Position(x, y));
        }
        else{
            int flag = 0;
            for(int i=0;i<neighborList.size();i++){
                if ((neighborList.get(i).getRowIndex() == x && neighborList.get(i).getColumnIndex() == y)) {
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                neighborList.add(new Position(x, y));
            }
        }
        return neighborList;
    }
}
