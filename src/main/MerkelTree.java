import java.util.ArrayList;
import java.util.Comparator;

public class MerkelTree {

    protected File file;

    /**
     * The root node
     */
    protected Node root;

    /**
     * Creates a Merkel Tree from an array of events (with the index of the first event equals to 0)
     * @param events Array of events
     */
    public MerkelTree(String[] events) {
        this(events, 0);
    }

    /**
     * Creates a Merkel Tree from an array of events
     * @param events Array of events
     * @param initialIndex The index of the first event
     */
    public MerkelTree(String[] events, int initialIndex) {
        this.root = new Node(events[0], initialIndex);
        // we create the internal nodes
        for (int i = 1; i < events.length; i++) {
            // we create new internal node successively
            this.root = new Node(this.root, new Node(events[i], initialIndex + i));
        }
    }

    /**
     * Adds an event to the Merkel tree
     * @param event Event to add to the tree
     */
    public void addEvent(String event) {
        // We create a new root node with the new event (having index equals to the last index + 1 )
        this.root = new Node(this.getRoot(), new Node(event, this.getRoot().getEndIndex() + 1));
    }

    /**
     * Returns path to leaf
     * @param i index researched
     * @return Path to the leaf node
     * @exception Exception The index is invalid
     */
    public ArrayList<AuditHash> getPathToLeaf(int i) throws Exception {
        // has the index start by 0
        i = i -1;
        // we check that i is inferior or equals to n
        if(i > this.getRoot().getEndIndex()){
            throw new Exception("i must be inferior to n");
        }
        ArrayList<AuditHash> results = new ArrayList<>();
        boolean isIndexFound = false;
        Node navigatorNode = this.getRoot();
        while (!isIndexFound){
            // we check if the left subtree has the node with the index
            if(navigatorNode.getLeftNode().getStartIndex() <= i && i <= navigatorNode.getLeftNode().getEndIndex()){
                // it's the left tree that contains the searched node
                // we adds the hash of the other node (right node so) to the array of hashes
                results.add(0, new AuditHash(navigatorNode.getRightNode().getHash(), AuditHashTypeEnum.RIGHT));
                // next tree to analyze is the left one
                navigatorNode = navigatorNode.getLeftNode();
            } else {
                // it's then the right tree that contains the searched node
                // so we add the left node hash
                results.add(0, new AuditHash(navigatorNode.getLeftNode().getHash(), AuditHashTypeEnum.LEFT));
                // next tree to analyze is the right node
                navigatorNode = navigatorNode.getRightNode();
            }
            // check if the next subtree is a leaf or not
            if(navigatorNode.isALeaf()) {
                // we have found the researched node
                isIndexFound = true;
            }
        }
        return results;
    }

    /**
     * Returns the consistency proof hashes array
     * @param i The size of the subtree
     * @return The consistency proof hashes array
     * @throws Exception The index is invalid
     */
    public ArrayList<byte[]> genProof(int i) throws Exception {
        // has the index start by 0
        i = i -1;
        // we check that i is inferior or equals to n
        if(i > this.getRoot().getEndIndex()){
            throw new Exception("i must be inferior to n");
        }
        // store the result nodes
        ArrayList<Node> resultNodes = new ArrayList<>();
        boolean isProofGenerated = false;
        // set a navigator node
        Node navigatorNode = this.getRoot();
        while (!isProofGenerated){
            Node newSelectorNode = null;
            // we check if the left subtree has the required size
            if(navigatorNode.getLeftNode().getEndIndex() <= i){
                // the left tree is large enough
                // we adds the hash of the tree to the array of hashes
                resultNodes.add(navigatorNode.getLeftNode());
            } else {
                // we need to dig into the left tree
                newSelectorNode = navigatorNode.getLeftNode();
            }
            // we check if the right subtree has the required size
            if(navigatorNode.getRightNode().getStartIndex() > i){
                // it's then the left node that contains the searched node
                resultNodes.add(navigatorNode.getRightNode());
            } else {
                // we need to dig into the right tree
                newSelectorNode = navigatorNode.getRightNode();
            }
            // check if the next subtree is a leaf or not
            if (newSelectorNode == null || newSelectorNode.isALeaf()) {
                // we have found the researched node
                isProofGenerated = true;
            } else {
                // we update the navigator node
                navigatorNode = newSelectorNode;
            }
        }
        // sort the node results by the indexes
        resultNodes.sort(Comparator.comparingInt(Node::getEndIndex).reversed());
        // we return the hashes
        ArrayList<byte[]> hashesResult = new ArrayList<>();
        for(Node result: resultNodes) {
            hashesResult.add(result.getHash());
        }
        return hashesResult;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
