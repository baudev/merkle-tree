import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MerkelTreeTest {

    @Test
    void MerkelTree() {
        // we count the number of events into the test file
        File testFile = new File("logs.txt");
        String[] events = testFile.readFileContent().split("\n");
        int numberOfEvents = events.length;

        // create the Merkle Tree
        MerkelTree tree = new MerkelTree(events);

        // Check that the index are goods
        assertEquals(0, tree.getRoot().getStartIndex());
        assertEquals(numberOfEvents - 1, tree.getRoot().getEndIndex());

    }

    @Test
    void addEvent() {
        // we count the number of events into the test file
        File testFile = new File("logs.txt");
        String[] events = testFile.readFileContent().split("\n");
        int numberOfEvents = events.length;

        // create the Merkle Tree
        MerkelTree tree = new MerkelTree(events);

        // we add an event to the tree
        tree.addEvent("test");
        // Check that the index are goods
        assertEquals(0, tree.getRoot().getStartIndex());
        assertEquals(numberOfEvents, tree.getRoot().getEndIndex());
    }

    @Test
    public void getPathToLeafOnSimpleTree() throws Exception {
        MerkelTree merkelTree = new MerkelTree(new File("logs.txt").readFileContent().split("\n"));
        // get the array of hashes
        ArrayList<AuditHash> arrayOfHashes = merkelTree.getPathToLeaf(1);

        // count the theoretical number of elements in the recovery path
        int numberOfHashes = merkelTree.getRoot().getEndIndex();
        assertEquals(numberOfHashes, arrayOfHashes.size());

        // checks the different hashes
        Node nodeSelector = merkelTree.getRoot();
        for(int j = arrayOfHashes.size() - 1; j == 0; j--)
        {
            assertEquals(nodeSelector.getRightNode().getHash(), arrayOfHashes.get(j).getValue());
            nodeSelector = nodeSelector.getLeftNode().getRightNode();
        }
    }

    @Test
    public void getPathToLeafOnComplicatedTree() throws RemoteException {
        MerkelTree merkelTree = new MerkelTree(new File("logs.txt").readFileContent().split("\n"));
        String[] newEvents = new String[]{"TEST1", "TEST2"};
        // add batch of events
        MerkelTree tempTree = new MerkelTree(newEvents, merkelTree.getRoot().getEndIndex() + 1);
        // we merge the two merkel trees
        merkelTree.setRoot(new Node(merkelTree.getRoot(), tempTree.getRoot()));

        // create MerkleTree of the new events
        MerkelTree newTempTree = new MerkelTree(newEvents);

        // check the root hash of the current merkle tree
        assertArrayEquals(merkelTree.getRoot().getHash(), new Node(merkelTree.getRoot().getLeftNode(), newTempTree.getRoot()).getHash());

    }

    @Test
    void genProof() throws Exception {
        MerkelTree tree1 = new MerkelTree(new String[]{"EVENT1", "EVENT2"}, 0);
        MerkelTree tree2 = new MerkelTree(new String[]{"EVENT3", "EVENT4"}, 2);
        MerkelTree tree3 = new MerkelTree(new String[]{"EVENT5", "EVENT6"}, 4);
        MerkelTree tree4 = new MerkelTree(new String[]{"EVENT7", "EVENT8"}, 6);
        // merge the trees
        tree1.setRoot(new Node(tree1.getRoot(), tree2.getRoot()));
        tree3.setRoot(new Node(tree3.getRoot(), tree4.getRoot()));
        // merge the trees
        tree1.setRoot(new Node(tree1.getRoot(), tree3.getRoot()));

        ArrayList<byte[]> hashesResult = tree1.genProof(6);
        // check that the array of hashes is valid
        assertArrayEquals(hashesResult.get(0), tree1.getRoot().getRightNode().getRightNode().getHash());
        assertArrayEquals(hashesResult.get(1), tree1.getRoot().getRightNode().getLeftNode().getHash());
        assertArrayEquals(hashesResult.get(2), tree1.getRoot().getLeftNode().getHash());
    }

}