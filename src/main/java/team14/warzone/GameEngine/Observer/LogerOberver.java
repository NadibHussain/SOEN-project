package team14.warzone.GameEngine.Observer;

import team14.warzone.Console.Console;
import team14.warzone.GameEngine.GameEngine;
import team14.warzone.GameEngine.Player;
import team14.warzone.GameEngine.State.Phase;
import team14.warzone.MapModule.Continent;
import team14.warzone.MapModule.Country;
import team14.warzone.MapModule.Map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Observer LoggerObserver
 */
public class LogerOberver implements Observer{

    LogEntryBuffer d_CurrentLoggerBufferEntry;

    /**
     * Loggerobserver
     */
    public LogerOberver(){
        d_CurrentLoggerBufferEntry = new LogEntryBuffer();
    }

    /**
     * update method
     * @param p_observable the observable whos state has changed
     */
    @Override
    public void update(Observable p_observable) {

        LogEntryBuffer l_LoggerBufferEntry = (LogEntryBuffer)p_observable;
        if (l_LoggerBufferEntry.getD_log()!=d_CurrentLoggerBufferEntry.getD_log())
        {
            writeLogs(l_LoggerBufferEntry.getD_log());
        }
        d_CurrentLoggerBufferEntry = new LogEntryBuffer(l_LoggerBufferEntry);

    }


    /**
     * Writes logs file inside logs.txt file
     * @param p_Log the log message that needs to be printed in the logs.txt file
     */
    private static void writeLogs(String p_Log)
    {
        String l_ExistingLogs="";
        try {
            File l_file = new File("./logs.txt");
            FileInputStream l_FileInputStream = new FileInputStream(l_file);
            byte[]data = new byte[(int) l_file.length()];
            l_FileInputStream.read(data);
            l_FileInputStream.close();
            l_ExistingLogs = new String(data, "UTF-8");

        } catch (IOException e) {
            System.out.println("new logs file created");
        }

        try {
            FileWriter l_Writer = new FileWriter("logs.txt");
            l_Writer.write(l_ExistingLogs+"\n"+p_Log);
            l_Writer.close();
        } catch (IOException l_IOException) {
            System.out.println("An error occurred.");
            l_IOException.printStackTrace();
        }
    }


}
