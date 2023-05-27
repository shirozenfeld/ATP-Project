package algorithms.mazeGenerators;

import javafx.geometry.Pos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.nio.ByteBuffer;
/**

 A Maze class that represents a maze with its dimensions, cell values, start and end positions.
 */
public class Maze implements Serializable {

    private int columns;
    private int rows;
    private int[][] mazeMatrix;
    private Position startPosition;
    private Position endPosition;
    /**
     * Constructor that initializes a new instance of Maze class with the given parameters.
     */
    public Maze(int[][] mazeMatrix, Position startPosition, Position endPosition) {
        this.rows = mazeMatrix.length;
        this.columns = mazeMatrix[0].length;
        this.mazeMatrix = mazeMatrix;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Maze (byte[] b) {

//    byteToBinaryString      mergeBinaryStrings

        String s1,s2,s3,s4;

        s1 = byteToBinaryString(b[0]);
        s2 = byteToBinaryString(b[1]);
        s3 = byteToBinaryString(b[2]);
        s4 = byteToBinaryString(b[3]);
        int startX = mergeBinaryStrings(s1,s2,s3,s4);

        s1 = byteToBinaryString(b[4]);
        s2 = byteToBinaryString(b[5]);
        s3 = byteToBinaryString(b[6]);
        s4 = byteToBinaryString(b[7]);
        int startY = mergeBinaryStrings(s1,s2,s3,s4);
        this.startPosition = new Position(startX, startY);

        s1 = byteToBinaryString(b[8]);
        s2 = byteToBinaryString(b[9]);
        s3 = byteToBinaryString(b[10]);
        s4 = byteToBinaryString(b[11]);
        int endX = mergeBinaryStrings(s1,s2,s3,s4);

        s1 = byteToBinaryString(b[12]);
        s2 = byteToBinaryString(b[13]);
        s3 = byteToBinaryString(b[14]);
        s4 = byteToBinaryString(b[15]);
        int endY = mergeBinaryStrings(s1,s2,s3,s4);
        this.endPosition = new Position(endX, endY);

        s1 = byteToBinaryString(b[16]);
        s2 = byteToBinaryString(b[17]);
        s3 = byteToBinaryString(b[18]);
        s4 = byteToBinaryString(b[19]);
       this.rows = mergeBinaryStrings(s1,s2,s3,s4);

        s1 = byteToBinaryString(b[20]);
        s2 = byteToBinaryString(b[21]);
        s3 = byteToBinaryString(b[22]);
        s4 = byteToBinaryString(b[23]);
        this.columns = mergeBinaryStrings(s1,s2,s3,s4);

        int index = 24;
        int [][] build = new int[rows][columns];
        for (int i = 0; i<rows; i++){
            for (int j=0; j<columns; j++) {
                build[i][j] = (int) b[index];
                index++;
            }
        }
        this.mazeMatrix = build;

    }

    //returns the value in the given cell 0/1
    public int getMazeCellValue(int row, int column){
        return this.mazeMatrix[row][column];
    }

    //Returns a string representation of the Maze object.
    @Override
    public String toString() {
        return "Maze{" +
                "maze=" + Arrays.toString(mazeMatrix) +
                '}';
    }

    /**
     * Prints the Maze object.
     */
    public void print() {
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                if(i==0 && j==0){
                    System.out.print("S");
                }
                else if(i==rows-1 && j==columns-1){
                    System.out.print("E");
                }
                else{
                    System.out.print(this.mazeMatrix[i][j]);
                }
            }
            System.out.println();
        }
    }

    //get functions
    public Position getStartPosition(){
        return this.startPosition;
    }

    public Position getGoalPosition(){
        return this.endPosition;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int[][] getMazeMatrix() {
        return mazeMatrix;
    }

    public byte[] toByteArray()
    {
        int add = 32 - ((this.rows*this.columns) % 32);
        byte[] b=new byte[24 + this.rows*this.columns + add];
        int offset = 0;
        int start=0;
        int end=0;


        String startX = intToBinaryString(this.startPosition.getColumnIndex());
        String startY = intToBinaryString(this.startPosition.getRowIndex());
        String endX = intToBinaryString(this.endPosition.getColumnIndex());
        String endY = intToBinaryString(this.endPosition.getRowIndex());
        String rows = intToBinaryString(this.rows);
        String columns = intToBinaryString(this.columns);

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(startX.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(startY.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(endX.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(endY.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(rows.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        start=0;
        end=8;
        for (int j=0; j<4; j++) {
            b[offset] = binaryStringToByte(columns.substring(start,end));
            start+=8;
            end+=8;
            offset++;
        }

        for (int i = 0; i<this.rows; i++) {
            for (int j=0; j<this.columns; j++) {
                b[offset] = (byte)this.mazeMatrix[i][j];
                offset++;
            }
        }

        return b;





    }

    public String intToBinaryString(int number) {
        StringBuilder binaryString = new StringBuilder();

        for (int i = 31; i >= 0; i--) {
            int mask = 1 << i;
            int bit = (number & mask) != 0 ? 1 : 0;
            binaryString.append(bit);
        }

        return binaryString.toString();
    }

    public byte binaryStringToByte(String binaryString) {
        int decimalValue = Integer.parseInt(binaryString, 2);
        return (byte) decimalValue;
    }

    public int mergeBinaryStrings(String s1, String s2, String s3, String s4) {
        String mergedString = s1 + s2 + s3 + s4; // Concatenate the four strings

        // Parse the merged string as an integer using base 2 (binary)
        int mergedValue = Integer.parseInt(mergedString, 2);

        return mergedValue;
    }
    public String byteToBinaryString(byte value) {
        String binaryString = Integer.toBinaryString(value & 0xFF); // Convert to binary string

        // Pad the binary string with leading zeros to ensure a size of 8
        while (binaryString.length() < 8) {
            binaryString = "0" + binaryString;
        }

        return binaryString;
    }


//    public byte[] intToByteArray(int number, int size) {
//        BigInteger bigInt = BigInteger.valueOf(number);
//        return bigInt.toByteArray();
////        byte[] byteArray = new byte[size];
////
////        for (int i = size-1; i >= 0; i--) {
////            byteArray[i] = (byte) (number & 0xFF);
////            number >>= 8;
////        }
////
////        return byteArray;
//    }

}

