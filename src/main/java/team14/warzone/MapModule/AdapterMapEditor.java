package team14.warzone.MapModule;

public class AdapterMapEditor extends MapEditorDomination{

    MapEditorConquest d_MapEditorConquest;

    /**
     * Constructor which takes map editor conquest as local variable
     * @param l_MapEditorConquest takes MapEditorConquest to adapt with map editor domination
     */
    public AdapterMapEditor(MapEditorConquest l_MapEditorConquest){
        this.d_MapEditorConquest = l_MapEditorConquest;
    }
}
