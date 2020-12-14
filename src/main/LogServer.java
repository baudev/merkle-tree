import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LogServer extends UnicastRemoteObject implements LogServerInterface {

    protected MerkelTree merkelTree;
    protected File logFile;

    /**
     * Construct a MerkelTree from the log file
     * @param logFileName Name of the log file
     */
    public LogServer(String logFileName) throws RemoteException {
        super();
        this.logFile = new File(logFileName);
        this.merkelTree = new MerkelTree(logFile.readFileContent().split("\n"));
    }

    /**
     * Returns the current root hash of the tree
     * @return Root hash of the merkel tree
     */
    public byte[] getRootHash() {
        return this.getMerkelTree().getRoot().getHash();
    }

    /**
     * Adds event to the current merkel tree
     * @param event Event to be added
     */
    public void addEvent(String event) {
        this.getMerkelTree().addEvent(event);
    }

    /**
     * Adds a batch of events to the current merkel tree
     * @param events Batch of events
     */
    public void addBatchOfEvents(String[] events) {
        // we create a merkel tree with the batch of events
        MerkelTree tempTree = new MerkelTree(events, this.getMerkelTree().getRoot().getEndIndex() + 1);
        // we merge the two merkel trees
        this.getMerkelTree().setRoot(new Node(this.getMerkelTree().getRoot(), tempTree.getRoot()));
    }

    /**
     * Returns the audit path for the i-th event
     * @param i i-th event index to audit
     * @return the audit path
     * @throws Exception When the index is invalid
     */
    public ArrayList<AuditHash> genPath(int i) throws Exception {
        // we return the array of hashes
        return this.getMerkelTree().getPathToLeaf(i);
    }

    /**
     * Returns the consistency proof path for the i-th event
     * @param i i-th event
     * @return the consistency proof path
     * @throws Exception When the index is invalid
     */
    public ArrayList<byte[]> genProof(int i) throws Exception {
        return this.getMerkelTree().genProof(i);
    }

    public MerkelTree getMerkelTree() {
        return merkelTree;
    }

    public void setMerkelTree(MerkelTree merkelTree) {
        this.merkelTree = merkelTree;
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }
}
