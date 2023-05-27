/*
package Server;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    Configurations config;
    Thread thread;
    ThreadPoolExecutor threadPoolExecutor;

    public Server(int port, int listeningIntervalms, IServerStrategy clientHandler) {
        try {
            this.config = Configurations.getInstance();
            this.port = port;
            this.listeningIntervalMS = listeningIntervalms;
            this.strategy = clientHandler;
            this.stop = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        new Thread(() -> {run();}).start();
//        thread = new Thread(this::run);
//        thread.start();
    }

    public void stop() {
        this.stop = true;
    }


    public void run()

    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            int numOfThreads = Configurations.getNumOfThreads();
            this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
            while (!stop) {
                try
                {
                    Socket clientSocket = serverSocket.accept();
                    threadPoolExecutor.submit(()-> {
                        Handle ( clientSocket ) ;} );
                }
                catch (SocketTimeoutException ignored)
                {
                    //t.printStackTrace();
                }
            }
            serverSocket.close();
            threadPoolExecutor.shutdownNow();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Handle(Socket clientSocket)
    {
        try
        {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
*/
/////////



package Server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.CORBA.TIMEOUT;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    Configurations config;


    public Server(int port, int listeningIntervalms, IServerStrategy clientHandler) {
        try {
            this.config = Configurations.getInstance();
            this.port = port;
            this.listeningIntervalMS = listeningIntervalms;
            this.strategy = clientHandler;
            //this.stop = false;


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void start() {
        Thread thread = new Thread(this::run);
        thread.start();
    }

    public void stop(){
        this.stop = true;
    }

    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningIntervalMS);
            int numOfThreads = Configurations.getNumOfThreads();
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
            while (!stop) {
                try {
                    Socket socket = server.accept();
                    threadPoolExecutor.execute(new myThread(socket, this.strategy));
                }
                catch (SocketTimeoutException ignored)
                {

                }
            }
            server.close();
            threadPoolExecutor.shutdown();
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

class myThread extends Thread
{
    Socket clientSocket;
    IServerStrategy strategy;

    public myThread(Socket clientSocket, IServerStrategy strategy){
        this.clientSocket = clientSocket;
        this.strategy = strategy;
    }

    public void run(){
        try
        {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}



