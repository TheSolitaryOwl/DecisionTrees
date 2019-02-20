import java.util.ArrayList;

public class Node
{
    public Node leftChild = null;
    public Node rightChild = null;
    public String label = null;
    private ArrayList<String> leftSubset = new ArrayList<String>();
    private ArrayList<String> rightSubset = new ArrayList<String>();

    Node (String label)
    {
        this.label = label;
    }
}
