import java.security.NoSuchAlgorithmException;

public class Node {

    protected byte[] hash;

    protected Node leftNode;
    protected Node rightNode;
    protected int startIndex;
    protected int endIndex;
    protected boolean isALeaf;

    /**
     * Constructor for a leaf
     * @param event String event
     */
    public Node(String event, int index) {
        // convert the event to bytes
        byte[] eventBytes = Helper.stringToBytes(event);
        // adds single byte 0x00
        eventBytes = Helper.concatBytesArray(new byte[]{0x00}, eventBytes);
        // compute the hash
        this.hash = Helper.encode(eventBytes);
        this.isALeaf = true;
        this.startIndex = index;
        this.endIndex = index;
    }

    /**
     * Constructor for an internal node
     * @param leftNode Node left node
     * @param rightNode Node right node
     */
    public Node(Node leftNode, Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        // concatenate the byte arrays and prepend it with 0x01
        byte[] eventBytes = Helper.concatBytesArray(new byte[]{0x01}, Helper.concatBytesArray(this.leftNode.getHash(), this.rightNode.getHash()));
        // compute the hash
        this.hash = Helper.encode(eventBytes);
        this.isALeaf = false;
        this.startIndex = leftNode.getStartIndex();
        this.endIndex = rightNode.getEndIndex();
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    public boolean isALeaf() {
        return isALeaf;
    }

    public void setALeaf(boolean ALeaf) {
        isALeaf = ALeaf;
    }

    public String getName(){
        return String.valueOf(this.getStartIndex()) + String.valueOf(this.getEndIndex());
    }
}
