package team14.warzone.GameEngine;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team14.warzone.GameEngine.Observer.LogEntryBuffer;
import team14.warzone.GameEngine.Observer.LogerOberver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for Observer
 */
public class ObserverTest {

    GameEngine d_GE;


    /**
     * Testing if observer can attach
     */
    @Test
    @DisplayName("Testing if observer can attach")
    public void observerAttachTest() {
        LogerOberver l_LogerObserver = new LogerOberver();
        LogEntryBuffer l_LogBufferEnrty = new LogEntryBuffer();
        int l_PreviousCount = l_LogBufferEnrty.getD_ObserverList().size();
        l_LogBufferEnrty.attach(l_LogerObserver);
        int l_NewCount = l_LogBufferEnrty.getD_ObserverList().size();
        assertEquals(l_NewCount, l_PreviousCount + 1);


    }

    /**
     * Testing if observer can detach
     */
    @Test
    @DisplayName("Testing if observer can detach")
    public void observerDetachTest() {
        LogerOberver l_LogerObserver = new LogerOberver();
        LogEntryBuffer l_LogBufferEnrty = new LogEntryBuffer();
        l_LogBufferEnrty.attach(l_LogerObserver);
        int l_PreviousCount = l_LogBufferEnrty.getD_ObserverList().size();
        l_LogBufferEnrty.detach(l_LogerObserver);
        int l_NewCount = l_LogBufferEnrty.getD_ObserverList().size();
        assertEquals(l_NewCount, l_PreviousCount - 1);


    }

    /**
     * Testing if observer can be Notified
     *
     * @throws FileNotFoundException throws exception when map file does not exist
     */
    @Test
    @DisplayName("Testing if observer can be Notified")
    public void observerNotifyTest() throws FileNotFoundException {
        LogerOberver l_LogerObserver = new LogerOberver();
        LogEntryBuffer l_LogBufferEnrty = new LogEntryBuffer();
        l_LogBufferEnrty.attach(l_LogerObserver);

        l_LogBufferEnrty.setD_log("new log test");
        l_LogBufferEnrty.notifyObservers(l_LogBufferEnrty);
        File l_FileObject = new File("logs.txt");
        Scanner l_ReaderObject = new Scanner(l_FileObject);
        String l_LastLine = "";
        while (l_ReaderObject.hasNextLine()) {
            l_LastLine = l_ReaderObject.nextLine();

        }
        assertEquals("new log test", l_LastLine);

    }
}
