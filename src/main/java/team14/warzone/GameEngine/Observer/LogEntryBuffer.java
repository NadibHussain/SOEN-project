package team14.warzone.GameEngine.Observer;

import java.util.ArrayList;
import java.util.List;

public class LogEntryBuffer extends Observable{


    private String d_log = "";

    /**
     * Default Constructor
     */
    public LogEntryBuffer()
    { }

    /**
     * Copy Constructor
     */
    public LogEntryBuffer(LogEntryBuffer p_LoggerBufferEntry)
    {
        this.d_log=p_LoggerBufferEntry.getD_log();
    }

    /**
     * Gets the corrent d_log
     */
    public String getD_log() {
        return d_log;
    }

    /**
     * Sets the corrent d_log
     * @param  d_log the string which is to be saved as log
     */
    public void setD_log(String d_log) {
        this.d_log = d_log;
    }


    /**
     * Retruns list of Observers which are attached to this observable
     */
    public List<Observer> getD_observers() {

        return d_observers;
    }


}
