import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LogServerInterface extends Remote {

    public byte[] getRootHash() throws RemoteException;

    public void addEvent(String event) throws RemoteException;

    public void addBatchOfEvents(String[] events) throws RemoteException;

    public ArrayList<AuditHash> genPath(int i) throws RemoteException, Exception;

    public ArrayList<byte[]> genProof(int i) throws RemoteException, Exception;

}
