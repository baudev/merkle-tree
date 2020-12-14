import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.*;

class LogServerTest {

    @Test
    public void LogServerConstructionOfMerkleTree() throws RemoteException {
        assertNotNull(new LogServer("logs.txt").getMerkelTree());
    }

    @Test
    public void addBatchOfEvents() throws RemoteException {
        LogServer logServer = new LogServer("logs.txt");
        // get the number of events
        int numberOfEvents = logServer.getMerkelTree().getRoot().getEndIndex();

        logServer.addBatchOfEvents(new String[]{"TEST1", "TEST2"});
        assertEquals(numberOfEvents + 2, logServer.getMerkelTree().getRoot().getEndIndex());
    }

}