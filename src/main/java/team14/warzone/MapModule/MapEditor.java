package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapEditor {

    public ArrayList<Object> d_loadedMap;

    public MapEditor(){

    }

    public void loadMap(String p_fileName){
        try {
            File myObj = new File(p_fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void saveMap(String p_fileName){

    }
    public void validateMap(ArrayList<Object> p_map){

    }
    public void addCountry(String p_name){

    }
    public void removeCountry(String p_name){

    }
    public void addContinent(String p_name){

    }
    public void removeContinent(String p_name){

    }
    public void addNeighbour(String p_name){

    }
    public void removeNeighbour(String p_name){

    }
}
