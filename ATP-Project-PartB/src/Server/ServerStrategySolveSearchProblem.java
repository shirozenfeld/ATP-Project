/*
package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private HashMap<String, Solution> mazeSolutions = new HashMap<>();


    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze maze = (Maze) fromClient.readObject();

            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            String request = maze.toString();
            Solution sol = null;
            if (mazeSolutions.containsKey(request)) {
                sol = mazeSolutions.get(request);
                sendSolutionToClient(sol, toClient); // Check if exist in cache memory

            } else {
                // Check if solution file exists
                if (solutionFileExists(tempDirectoryPath, request)) {  // If exist in file
                    Solution solution = retrieveSolutionFromFile(tempDirectoryPath, request);

                    // Store solution in memory
                    mazeSolutions.put(request, solution);

                    // Send solution to client
                    sendSolutionToClient(solution, toClient);

                } else {
                    // Generate maze and solve it
                    SearchableMaze searchableMaze = new SearchableMaze(maze);
                    ISearchingAlgorithm searchingAlgorithm = new BreadthFirstSearch(); // The chosen algo for solving the maze
                    Solution solution = searchingAlgorithm.solve(searchableMaze);

                    // Save solution to file
                    saveSolutionToFile(tempDirectoryPath, request, solution);
                    // Store solution in memory
                    mazeSolutions.put(request, solution);

                    // Send solution to client
                    sendSolutionToClient(solution, toClient);

                }
            }
            fromClient.close();
            toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // Method to send the solution to the client
    private void sendSolutionToClient(Solution solution, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(solution);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean solutionFileExists(String tempDirectoryPath, String request) {
        String uniqueFileName = getFileNameFromBinaryString(request);
        if (uniqueFileName == null) {
            return false;
        }
        File file = new File(tempDirectoryPath, uniqueFileName);
        return file.exists() && !file.isDirectory();
    }

    private Solution retrieveSolutionFromFile(String tempDirectoryPath, String request) {
        Solution solution = null;
        String uniqueFileName = getFileNameFromBinaryString(request);
        if (uniqueFileName == null) {
            return null;
        }
        File file = new File(tempDirectoryPath, uniqueFileName);
        try (FileInputStream inputFile = new FileInputStream(file);
             ObjectInputStream inputStream = new ObjectInputStream(inputFile)) {
            solution = (Solution) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return solution;
    }


    private void saveSolutionToFile(String tempDirectoryPath, String request, Solution solution) {
        try {
            String uniqueFileName = getFileNameFromBinaryString(request);
            if(uniqueFileName == null){
                return;
            }
            File file = new File(tempDirectoryPath, uniqueFileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(solution);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.err.println("An error occurred while saving the file: " + e.getMessage());
            e.printStackTrace();
            // Additional error handling code or alternative actions can be implemented here
        }
    }

    // Function to get a shorter filename to save in the temp directory.
    private String getFileNameFromBinaryString(String request) {
        try {
            byte[] binaryData = request.getBytes();
            // Generate hash using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(binaryData);

            // Convert hash bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            // Return the hexadecimal string as the file name
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle algorithm not found error
            e.printStackTrace();
            return null;
        }

    }
}
*/







package Server;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.nio.channels.Channels;

import java.io.*;
import java.util.HashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy
{
    public static int index=0;
    public static HashMap<String,String> HM = new HashMap<String,String>();
    private Configurations configurations;

    public ServerStrategySolveSearchProblem()
    {
        try {
            configurations = Configurations.getInstance();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            //toClient.flush();

            String tempDirectoryPath= System.getProperty("java.io.tmpdir");
            try {
                Maze m = (Maze) (fromClient.readObject());

                //System.out.println(m.toString());
                Solution s = null;
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                OutputStream out = new MyCompressorOutputStream(byteArrayOutputStream);
//                out.write(m.toByteArray());
                //the maze is not exist
                if (!(HM.containsKey(m.toString()))) {
                    String filePath = tempDirectoryPath + File.separator + index + ".ser";
                    //creating file for maze
                    FileOutputStream fos = new FileOutputStream(filePath);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    //get type of algorithm from property
                    ASearchingAlgorithm search = Configurations.getSearchAlgorithm();
                    s = search.solve(new SearchableMaze(m));
                    toClient.writeObject(s);
                    toClient.flush();
                    oos.writeObject(s);
                    oos.close();
                    HM.put(m.toString(), index + ".ser");
                    index++;
                } else {
                    //if the maze exist
                    String address = HM.get(m.toString());
                    FileInputStream fis = new FileInputStream(address);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    s = (Solution) ois.readObject();
                    toClient.writeObject(s);
                    fis.close();
                    ois.close();

                }
                fromClient.close();
                toClient.close();
            }
            catch (ClassNotFoundException c )
            {
                c.printStackTrace();
            }

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

}



//import algorithms. mazeGenerators. Maze;
//import algorithms.search.*;
//import java.io.*;
//import java.nio.channels. Channels;
//import java.util.Properties;
//
//public class ServerStrategySolveSearchProblem implements IServerStrategy{
///**
// * Implements the server strategy for solving a maze search problem based on client requests.
// * The solved maze solution is sent back to the client.
// *
// * @param inFromClient the input stream from the client
// * @param outToClient the output stream to the client
// * @throws I0Exception if an I/0 error occUrs
// */
//    @Override
//    public void ServerStrategy (InputStream inFromClient, OutputStream outToClient) throws I0Exception{
//        try {
//        InputStream interruptibleInputStream = Channels.newInputStream(Channels.newChannel (inFromClient));
//        ObjectInputStream fromClient = new ObjectInputStream (interruptibleInputStream);
//        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
//        Maze maze_from_client = (Maze) fromClient.readobject();
//// Read the maze from the client
//        String mazeName = maze_from_client. getDetails0fMaze ();
//// Get the maze name and solution file path
//        String solutionFilePath = getSolutionFilePath (mazeName);
//        File file = new File (solutionFilePath);
//        if (file.exists()){
//// If a solution file already exists, send the existing solution back to the client
//            Solution existingSolution = readSolutionFromFile (file);
//            toClient.write0bject(existingSolution);
//            toClient. flushO;
//            fromClient. close ();
//            toClient.close;
//        } else {
//            String searching_name = "";
//            try (InputStream input = new FileInputStream ( name: "resources/config.properties")) {
//                Properties prop = new Properties () ;
//                prop. load (input);
//                searching_name = prop.getProperty ("mazeSearchingAlgorithm");
//            } catch (IOException ex) {
//                ex.printStackTrace ();
//                SearchableMaze searchable_maze = new SearchableMaze (maze_from_client);
//// Create the specified searching algorithm
//                ISearchingAlgorithm searchingAlgorithm = new BreadthFirstSearch();
//                if (searching_name. toLowerCase () . equals ("breadthfirstsearch"))
//                    searchingAlgorithm = new BreadthFirstSearch ();
//            } else if (searching_ name. toLowerCase () .equals ("bestfirstsearch")) {
//                searchingAlgorithm = new BestFirstSearch ();
//            } else if (searching_name. toLowerCase () . equals ("depthfirstsearch")) {
//                searchingAlgorithm = new DepthFirstSearch();

