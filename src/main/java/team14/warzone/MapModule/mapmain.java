package team14.warzone.MapModule;

public class mapmain {
    public static void main (String []args){
        MapEditor me = new MapEditor();
        me.loadMap("D:/Concordia Courses/SOEN 6441/Project Tools/bigeurope.map");
        
        Map map = new Map();
        map = me.getD_loadedMap();
        map.showMap();
    }
}