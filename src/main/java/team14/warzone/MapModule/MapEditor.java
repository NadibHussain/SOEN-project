package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapEditor {

    public ArrayList<Object> d_loadedMap;

    public MapEditor(){

    }

    public Map loadMap(String p_fileName){
        Map map =  new Map();
        try {
            File myObj = new File(p_fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("[countries]")){
                    while (true){
                        String l_line = myReader.nextLine();
                        ArrayList<Continent> l_continentList = map.getD_continents();
                        if(l_line.equals(""))
                        {
                            break;
                        }
                        else {
                            String[] l_country_array = l_line.split(" ");
                            String continentName= "";
                            for (Continent l_conrinent: l_continentList) {
                                if(l_conrinent.getID()==Integer.parseInt(l_country_array[2])){
                                    continentName = l_conrinent.getName();
                                }
                            }
                            map.addCountry(l_country_array[1],Integer.parseInt(l_country_array[0]),continentName);
                        }
                    }
                }
                else if (data.equals("[continents]"))
                {
                    int id = 1;
                    while (true){
                        String l_line = myReader.nextLine();
                        if(l_line.equals(""))
                        {
                            break;
                        }
                        else {
                            String[] l_continent_array = l_line.split(" ");
                            map.addContinent(l_continent_array[0],id,Integer.parseInt(l_continent_array[1]));
                            id++;
                        }
                    }
                }
                else if (data.equals("[borders]"))
                {
                    ArrayList<Country> l_countires = map.getD_countries();
                    int l_index = 0;
                    while (myReader.hasNextLine()){
                        String l_line = myReader.nextLine();
                        if(l_line.equals(""))
                        {
                            break;
                        }
                        else {
                            String[] l_neighbour_array = l_line.split(" ");
                            ArrayList<String> l_neighbourID_array = new ArrayList<String>();
                            for (int x=1;x<l_neighbour_array.length;x++){
                                l_neighbourID_array.add(l_neighbour_array[x]);
                            }
                            l_countires.get(l_index).setD_neighbours(l_neighbourID_array);
                            l_index++;
                        }
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return map;

    }
    public void saveMap(String p_fileName){

    }

    /**
     * Validates the map object
     * @param p_map
     */
    public boolean validateMap(ArrayList<Object> p_map){

        /**
         * checking if the map is connected
         */
        for (int count = 0; count<p_map.size() ; count++ ){
//            if( )

        }
        return false;
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
