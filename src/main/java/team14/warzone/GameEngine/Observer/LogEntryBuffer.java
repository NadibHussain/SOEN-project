package team14.warzone.GameEngine.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable LogEntryBuffer
 */
public class LogEntryBuffer extends Observable implements Serializable {

    /**
     * String for logs
     */
    private String d_log = "";

    /**
     * empty LogEntryBuffer
     */
    public LogEntryBuffer()
    { }

    /**
     * constructor for LogEntryBuffer
     * @param p_LoggerBufferEntry LogEntryBuffer
     */
    public LogEntryBuffer(LogEntryBuffer p_LoggerBufferEntry)
    {
        this.d_log=p_LoggerBufferEntry.getD_log();
    }

    /**
     * getter
     * @return log texts
     */
    public String getD_log() {
        return d_log;
    }

    /**
     * setter
     * @param d_log log texts
     */
    public void setD_log(String d_log) {
        this.d_log = d_log;
    }

    /**
     * Returns the list of observers which are attached
     * @return List of observers
     */
    public List<Observer> getD_ObserverList(){
        return d_observers;
    }
}
