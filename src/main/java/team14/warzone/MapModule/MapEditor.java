package team14.warzone.MapModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapEditor {

    public Map d_loadedMap;

    public MapEditor(){

    }

    public void loadMap(String p_fileName){
        Map l_map =  new Map();
        try {
            File myObj = new File(p_fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("[countries]")){
                    while (true){
                        String l_line = myReader.nextLine();
                        ArrayList<Continent> l_continentList = l_map.getD_continents();
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
                            l_map.addCountry(l_country_array[1],Integer.parseInt(l_country_array[0]),continentName);
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
                            l_map.addContinent(l_continent_array[0],id,Integer.parseInt(l_continent_array[1]));
                            id++;
                        }
                    }
                }
                else if (data.equals("[borders]"))
                {
                    ArrayList<Country> l_countires = l_map.getD_countries();
                    int l_index = 0;
                    while (myReader.hasNextLine()){
                        String l_line = myReader.nextLine();
                        if(l_line.equals(""))
                        {
                            break;
                        }
                        else {
                            String[] l_neighbour_array = l_line.split(" ");
                            ArrayList<Country> l_neighbourID_array = new ArrayList<Country>();
                            for (int x=1;x<l_neighbour_array.length;x++){
                                for(int j = 0;j<l_countires.size();j++)
                                {
                                    if (l_countires.get(j).getID() == Integer.parseInt(l_neighbour_array[x]))
                                    {
                                        l_neighbourID_array.add(l_countires.get(j));
                                    }
                                }
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
        this.d_loadedMap = l_map;

    }
    public void saveMap(String p_fileName){

    }
    public void validateMap(ArrayList<Object> p_map){

    }

    public Map getD_loadedMap() {
        return d_loadedMap;
    }

}
