package team14.warzone.GameEngine.Observer;

import java.util.ArrayList;

public class LogEntryBuffer extends Observable{


    private String d_log = "";

    public LogEntryBuffer()
    { }
    public LogEntryBuffer(LogEntryBuffer p_LoggerBufferEntry)
    {
        this.d_log=p_LoggerBufferEntry.getD_log();
    }

    public String getD_log() {
        return d_log;
    }

    public void setD_log(String d_log) {
        this.d_log = d_log;
    }
}
