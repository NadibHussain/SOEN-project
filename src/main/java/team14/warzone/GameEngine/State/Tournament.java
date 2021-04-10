package team14.warzone.GameEngine.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.MapModule.Map;
import team14.warzone.MapModule.MapEditor;

public class Tournament extends Phase{
    private ArrayList<Map> d_Maps;
    private ArrayList<Player> d_Players;
    private int d_NumOfGames;
    private int d_NumOfTurns;
    private MapEditor d_MapEditor;

    public Tournament(GameEngine p_GameEngine){
        super(p_GameEngine);
        d_MapEditor = new MapEditor();
    }
    
    /** 
     * @param p_MapList
     */
    public void tournamentAddMaps(List<String> p_MapList){
        for(String p_MapFileName:p_MapList){
            try {
                d_MapEditor.loadMap(p_MapFileName);
            } catch (FileNotFoundException e) {
                System.out.println(p_MapFileName+" file does not exist.");
                e.printStackTrace();
            }
            if(d_MapEditor.validateMap(d_MapEditor.getD_LoadedMap()))
                d_Maps.add(d_MapEditor.getD_LoadedMap());
                else System.out.println(p_MapFileName+" map is not valid.");
        }
    }
    
    @Override
    public void run() {
        for(Map l_map : d_Maps){
            for(int l_Index = 0; l_Index<d_NumOfGames; l_Index++){
                
            }
        }
    }
    
    /** 
     * @param p_CountryId
     * @param p_ContinentId
     */
    @Override
    public void addCountry(String p_CountryId, String p_ContinentId) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_CountryId
     */
    @Override
    public void removeCountry(String p_CountryId) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_ContinentId
     * @param p_ControlValue
     */
    @Override
    public void addContinent(String p_ContinentId, int p_ControlValue) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_ContinentId
     */
    @Override
    public void removeContinent(String p_ContinentId) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void addNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_CountryId
     * @param p_NeighborId
     */
    @Override
    public void removeNeighbor(String p_CountryId, String p_NeighborId) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_FileName
     */
    @Override
    public void loadMap(String p_FileName) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_FileName
     */
    @Override
    public void saveMap(String p_FileName) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_FileName
     */
    @Override
    public void editMap(String p_FileName) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_Map
     */
    @Override
    public void validateMap(Map p_Map) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_Name
     * @param p_PlayerType
     */
    @Override
    public void addPlayer(String p_Name, String p_PlayerType) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_Name
     */
    @Override
    public void removePlayer(String p_Name) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void assignCountries() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void reinforce() {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_FileName
     */
    @Override
    public void saveGame(String p_FileName) {
        // TODO Auto-generated method stub
        
    }
    
    /** 
     * @param p_FileName
     */
    @Override
    public void loadGame(String p_FileName) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void issueCommands() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void showCards() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void executeCommands() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void deploy() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void advance() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void bomb() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void blockade() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void airlift() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void diplomacy() {
        // TODO Auto-generated method stub
        
    }
    
}
