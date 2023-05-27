package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configurations
{
    private static Configurations instance=null;
    private static Properties prop =new Properties();;
    private static InputStream in;
    private static OutputStream out;

    private Configurations() throws IOException
    {

        out = Files.newOutputStream(Paths.get("resources/config.properties"));
        prop.setProperty("mazeGenerate", "MyMazeGenerator");
        prop.setProperty("searchAlgorithm", "BestFirstSearch");
        prop.setProperty("numberOfThreads", "4");
        prop.store(out, null);

    }
    public static Configurations getInstance() throws IOException {
        if(instance==null)
            instance=new Configurations();
        return instance;
    }

   public static int getNumOfThreads() throws IOException
   {
       in = Files.newInputStream(Paths.get("resources/config.properties"));
       prop.load(in);
       String num=prop.getProperty("numberOfThreads");
       return Integer.parseInt(num);
   }

   public static ASearchingAlgorithm getSearchAlgorithm() throws IOException{
       in = Files.newInputStream(Paths.get("resources/config.properties"));
       prop.load(in);
       String searchAlgorithm=prop.getProperty("searchAlgorithm");
       ASearchingAlgorithm searchingAlgorithm = null;
       if(searchAlgorithm.equals("BestFirstSearch")){
           searchingAlgorithm = new BestFirstSearch();
       }
       if(searchAlgorithm.equals("BreadthFirstSearch")){
           searchingAlgorithm = new BreadthFirstSearch();
       }
       if(searchAlgorithm.equals("DepthFirstSearch")){
           searchingAlgorithm = new DepthFirstSearch();
       }
       return searchingAlgorithm;
   }

   public static AMazeGenerator getMazeGenerator() throws IOException {
       in = Files.newInputStream(Paths.get("resources/config.properties"));
       prop.load(in);
       String mazeGenerate=prop.getProperty("mazeGenerate");
       AMazeGenerator mazeGenerator = null;
       if(mazeGenerate.equals("MyMazeGenerator")){
           mazeGenerator = new MyMazeGenerator();
       }
       if(mazeGenerate.equals("SimpleMazeGenerator")){
            mazeGenerator = new SimpleMazeGenerator();
       }
       return mazeGenerator;
   }
    /*public String getProp(String key)
    {
        //gets the properties from the configuration file
        java.util.Properties prop = new java.util.Properties();
        InputStream input;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            return prop.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
