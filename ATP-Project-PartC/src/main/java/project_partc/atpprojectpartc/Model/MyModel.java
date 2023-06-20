package project_partc.atpprojectpartc.Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.search.AState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project_partc.atpprojectpartc.View.MyViewController;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyModel extends Observable implements IModel {
    private Server mazeGeneratingServer;
    private final Logger LOG = LogManager.getLogger();

    public Server getMazeGeneratingServer() {
        return mazeGeneratingServer;
    }

    public Server getSolveSearchProblemServer() {
        return solveSearchProblemServer;
    }

    private Server solveSearchProblemServer;
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private Maze maze;
    private int characterPositionRow;
    private int characterPositionColumn;
    public Solution getS() {
        return s;
    }
    private Solution s;

    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        LOG.info("Starting server at port 5400");
        LOG.info("Starting server at port 5401");
        //Raise the servers
    }

    private void CommunicateWithServer_MazeGenerating(int row, int col) {
        try {
            Client e = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream e = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        e.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        e.writeObject(mazeDimensions);
                        e.flush();
                        byte[] compressedMaze = (byte[])((byte[])fromServer.readObject());
                        MyDecompressorInputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        //TODO figure the meaning of the decompress size
                        int add = 32 - row * col % 32;
                        byte[] decompressedMaze = new byte[24 + row*col + add];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        characterPositionRow=maze.getStartPosition().getRowIndex();
                        characterPositionColumn=maze.getStartPosition().getColumnIndex();
                        //maze.print();
                        ArrayList<AState> temp = new ArrayList<>();
                        s=new Solution(temp, 0);

                    } catch (Exception var10) {
                        LOG.error("Exception", var10);
                        var10.printStackTrace();
                    }

                }
            });
            e.communicateWithServer();
        } catch (UnknownHostException var1) {
            LOG.error("UnknownHostException", var1);
            var1.printStackTrace();
        }

    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client e = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream e = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        e.flush();
                        e.writeObject(maze);
                        e.flush();
                        s = (Solution)fromServer.readObject();
                        System.out.println(String.format("Solution steps: %s", new Object[]{s}));
                        ArrayList mazeSolutionSteps = s.getSolutionPath();

                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            e.communicateWithServer();
        } catch (UnknownHostException var1) {
            LOG.error("UnknownHostException", var1);
            var1.printStackTrace();
        }

    }

    public void startServers() {
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();

    }

    public void stopServers() {
        LOG.info("Servers stopped");
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //Platform.exit();
        System.exit(0);
    }

    @Override
    public  void openFile() {
        FileChooser choose = new FileChooser();
        choose.setTitle("open file");
        LOG.info("File opened");
        choose.setInitialDirectory(new File("./AllMaze"));
        File f = choose.showOpenDialog(null);
        if (f != null) {
            String s = f.getName();
            try {
                InputStream in = new FileInputStream("AllMaze/" + s);
                ObjectInputStream OS = new ObjectInputStream(in);
                Maze m = ((Maze) (OS.readObject()));
                int[] location = ((int[]) (OS.readObject()));
                this.maze = m;
                characterPositionRow = location[0];
                characterPositionColumn = location[1];
                setChanged();
                notifyObservers();
                MyViewController.sendToShow("successfully load!");
                LOG.info("File opened");
            } catch (IOException e) {
                LOG.error("Error while loading file");
                MyViewController.sendToShow("Error while load!");
            } catch (ClassNotFoundException e1) {
                LOG.error("Error while loading file");
                MyViewController.sendToShow("Error while load!");
            }
        }
    }

    public void solve() {
        LOG.info("Solving maze");
        threadPool.execute(() -> {
            CommunicateWithServer_SolveSearchProblem();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });
    }

    @Override
    public void generateMaze(int width, int height) {
        LOG.info("Generating maze");
        //Generate maze
        threadPool.execute(() -> {
            CommunicateWithServer_MazeGenerating(width, height);
            maze.print();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            setChanged();
            notifyObservers();

        });
    }

    public void stop(){
        stopServers();
    }
    @Override
    public Maze getMaze() {
        return maze;
    }

    public void setS(Solution s) {
        this.s = s;
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        int r = characterPositionRow;
        int c = characterPositionColumn;
        try {
            if ((movement == KeyCode.NUMPAD8 || movement == KeyCode.UP) && (maze.getMazeMatrix()[r - 1][c] != 1) && (r - 1 >= 0)) {
                characterPositionRow--;
            }
            else if ((movement == KeyCode.NUMPAD2 || movement == KeyCode.DOWN) && (maze.getMazeMatrix()[r + 1][c] != 1) && (r + 1 < maze.getMazeMatrix().length)) {
                characterPositionRow++;
            }
            else if ((movement == KeyCode.NUMPAD6 || movement == KeyCode.RIGHT) && (maze.getMazeMatrix()[r][c + 1] != 1) && (c + 1 < maze.getMazeMatrix()[0].length)) {
                characterPositionColumn++;
            }
            else if ((movement == KeyCode.NUMPAD4 || movement == KeyCode.LEFT) && (maze.getMazeMatrix()[r][c - 1] != 1) && (c - 1 >= 0)) {
                characterPositionColumn--;
            }




            else if ((movement == KeyCode.NUMPAD1) && (maze.getMazeMatrix()[r+1][c - 1] != 1) && (c - 1 >= 0) && (r + 1 < maze.getMazeMatrix().length) && (maze.getMazeMatrix()[r][c - 1] != 1 || maze.getMazeMatrix()[r+1][c] != 1)) {
                characterPositionRow++;
                characterPositionColumn--;
            }
            else if ((movement == KeyCode.NUMPAD3) && (maze.getMazeMatrix()[r+1][c + 1] != 1) && (c + 1 < maze.getMazeMatrix()[0].length) && (r + 1 < maze.getMazeMatrix().length) && (maze.getMazeMatrix()[r + 1][c] != 1 || maze.getMazeMatrix()[r][c+1] != 1)) {
                characterPositionRow++;
                characterPositionColumn++;
            }
            else if ((movement == KeyCode.NUMPAD7) && (maze.getMazeMatrix()[r-1][c - 1] != 1) && (c - 1 >= 0) && (r - 1 >= 0) && (maze.getMazeMatrix()[r][c - 1] != 1 || maze.getMazeMatrix()[r-1][c] != 1)) {
                characterPositionRow--;
                characterPositionColumn--;
            }

              else if ((movement == KeyCode.NUMPAD9) && (maze.getMazeMatrix()[r-1][c + 1] != 1) && (c + 1 < maze.getMazeMatrix()[0].length) && r - 1 >= 0 && (maze.getMazeMatrix()[r-1][c] != 1 || maze.getMazeMatrix()[r][c+1] != 1)) {
              characterPositionRow--;
              characterPositionColumn++;
          }


            setChanged();
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
}
