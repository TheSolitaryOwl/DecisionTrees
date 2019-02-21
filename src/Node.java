/********************************
 Name: Zachary Rowton
 Username: N/A
 Problem Set: PS2
 Due Date: February 21, 2019
 ********************************/
import java.util.ArrayList;

public class Node
{
    public Node leftChild = null;
    public Node rightChild = null;
    public String label = null;
    public boolean isLeaf = false;

    Node (String label)
    {
        this.label = label;
    }
}
