package team14.warzone.GameEngine;

import java.util.ArrayList;

import team14.warzone.MapModule.Map;

public class Tournament {
    private ArrayList<Map> d_Maps;
    private String d_ListOfMapFiles;
    private ArrayList<Player> d_Players;
    private String d_ListOfPlayerStrategies;
    private int d_NumOfGames;
    private int d_NumOfTurns;

    public Tournament(String p_ListOfMapFiles, String p_ListOfPlayerStrategies, int p_NumOfGames, int p_NumOfTurns){
        this.d_ListOfMapFiles = p_ListOfMapFiles;
        this.d_ListOfPlayerStrategies = p_ListOfPlayerStrategies;
        this.d_NumOfGames = p_NumOfGames;
        this.d_NumOfTurns = p_NumOfTurns;
    }
    public void play(){

        for(Map l_map : d_Maps){
            for(int l_Index = 0; l_Index<d_NumOfGames; l_Index++){
                
            }
        }
    }
    
}
