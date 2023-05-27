package Server;

import IO.MyCompressorOutputStream;
import IO.SimpleCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy
{
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient)
    {
        try
        {
            //input: an array of size 2 containing the dimensions of the maze
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] mazeDimensions = (int[]) fromClient.readObject();
            OutputStream out = new ByteArrayOutputStream();
            //output: an outputstream object, containing a compressed byte-array representation of the maze
            AMazeGenerator mazeGenerator = Configurations.getMazeGenerator();
            Maze clientMaze =mazeGenerator.generate(mazeDimensions[0],mazeDimensions[1]);
            byte[] byteMaze=clientMaze.toByteArray();
            //TODO: change to MyMaze
            MyCompressorOutputStream myCompressor=new MyCompressorOutputStream(out);
            //compressing the byte array and insert it into the byteArrayOutputStream
            myCompressor.write(byteMaze);
            toClient.writeObject(((ByteArrayOutputStream) myCompressor.out).toByteArray());
            //myCompressor.flush();
            toClient.flush();
            out.close();
            fromClient.close();
            toClient.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
