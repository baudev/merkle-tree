import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to audit a LogServer
 */
public class AuditorClass {

    protected LogServer logServer;
    protected int treeSize;
    protected byte[] treeRootHash;

    public AuditorClass(LogServer logServer) {
        this.logServer = logServer;
        // get the current size
        this.treeSize = this.logServer.getMerkelTree().getRoot().getEndIndex();
        this.treeRootHash = this.logServer.getRootHash();
    }

    /**
     * Verifies if the text event exists in the current log
     * @param event Event to check
     * @return If the event exists or not
     * @throws Exception
     */
    public boolean isMember(String event) throws Exception {
        // we create the node of the event
        Node eventNode = new Node(event, 0);
        // we compute all the possible audit paths as we don't know the index of the event
        for(int i = 1; i <= this.treeSize; i++) {
            ArrayList<AuditHash> tempHashPath = this.getLogServer().genPath(i);
            // we calculate the root hash from the audit path
            byte[] previousHash = eventNode.getHash();
            for(AuditHash tempHash: tempHashPath) {
                // calculate the hash
                // concatenate the hashes
                byte[] eventBytes;
                if(tempHash.getNodeType() == AuditHashTypeEnum.LEFT) {
                    eventBytes = Helper.concatBytesArray(new byte[]{0x01}, Helper.concatBytesArray(tempHash.getValue(), previousHash));
                } else {
                    eventBytes = Helper.concatBytesArray(new byte[]{0x01}, Helper.concatBytesArray(previousHash, tempHash.getValue()));
                }
                // compute the hash
                previousHash = Helper.encode(eventBytes);
            }
            // the root hashes are equivalent
            if(Arrays.equals(previousHash, this.treeRootHash)){
                return true;
            }
        }
        return false;
    }

    public LogServer getLogServer() {
        return logServer;
    }

    public void setLogServer(LogServer logServer) {
        this.logServer = logServer;
    }
}
