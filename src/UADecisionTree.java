/********************************
 Name: Zachary Rowton
 Username: N/A
 Problem Set: PS2
 Due Date: February 21, 2019
 ********************************/

import java.io.*;
import java.util.ArrayList;

public class UADecisionTree
{
    // Global variables
    private static int targetCol = 0;
    private static int numOfCols = 0;
    private static int maxTreeDepth = 0;
    private static double minimumImpurity = 0;
    private static ArrayList<String> recordList = new ArrayList<String>();
    private static ArrayList<String> labels = new ArrayList<String>();
    private static double entropy[];
    private static double informationGain[];
    static Node rootNode = null;
    static int currentDepth = 0;

    public static void main(String[] args) throws IOException
    {
        getTrainingMatrix("titanicdata.csv", "SURVIVED");
        setTreeMaxDepth(20);
        calculateEntropyForAllAttributes(numOfCols);  // gets the entropy for all attributes
        calculateInformationGainForAllAttributes(numOfCols); // gets information gain for all attributes
        determineRootNode(); // decides best attribute to split on and creates root node
        rootNode = train(recordList, new Node(null), labels);
    }

    public static void getTrainingMatrix (String fileName, String targetLabel) throws IOException
    {
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] record;
        int lineNum = 0;

        while ((line = bufferedReader.readLine()) != null)
        {
            record = line.split(",");

            // GET COLUMN WITH TARGET LABEL
            if (lineNum == 0)
            {
                for (String s : record)
                {
                    labels.add(s); // adds labels to list as they are read
                }

                // FOR STORING VALUES FOR EACH ATTRIBUTE
                informationGain = new double[record.length];
                entropy = new double[record.length];
                numOfCols = record.length;

                for (int i = 0; i < record.length; i++)
                {
                    if (record[i].equalsIgnoreCase(targetLabel))
                    {
                        targetCol = i;
                        labels.remove(i);
                    }
                }
            }
            else
            {
                recordList.add(line);
            }
            lineNum++;
        }

        bufferedReader.close();
    }

    public static void setTreeMaxDepth (int maxDepth)
    {
        maxTreeDepth = maxDepth;
    }

    public static void setMinimumImpurity (float minImpurity)
    {
        minimumImpurity = minImpurity;
    }

    public static Node train(ArrayList<String> dataSet, Node currentNode, ArrayList<String> attributes)
    {   //System.out.println("Train class has been called with a dataset size of:" + dataSet.size());
        //System.out.println(attributes.toString());
        Node current = currentNode;
        //System.out.println(current.label);
        // Lists for storing subset datasets to be passed onto child nodes
        ArrayList<String> leftSubset = new ArrayList<String>();
        ArrayList<String> rightSubset = new ArrayList<String>();
        // for attribute list with current attribute removed from calculation
        ArrayList<String> newAttributes = new ArrayList<String>(attributes);

        //double e[] = new double[attributes.size()];
        //double ig[] = new double[attributes.size()];

        // STOP CONDITIONS
        if (dataIsPure(dataSet))
        {
            // add leaf node
        }
        else if (currentDepth >= maxTreeDepth)
        {
            // add leaf node
        }

        // Otherwise, create node
        else
        {
            // find best attribute based on ig
            int bestAtt = 0;
            for (int i = 0; i < attributes.size(); i++)
            {
                if (informationGain[i] > bestAtt)
                {
                    bestAtt = i;
                }
            }

            if (newAttributes.size() > 0) newAttributes.remove(bestAtt);

            // split on attribute
            for (String row : dataSet)
            {
                String[] data = row.split(",");
                if (data[bestAtt].equalsIgnoreCase("1")); rightSubset.add(row);
                if (data[bestAtt].equalsIgnoreCase("0")); leftSubset.add(row);
            }

            current.rightChild = train(rightSubset, new Node(""), newAttributes);
            current.leftChild = train(leftSubset, new Node(""), newAttributes);
        }
        return current;
    }

    public static void classifyValue (String record)
    {
        // take in record and classify as either survived or not survived
    }

    private static double calculateEntropy(int col, ArrayList<String> dataSet)
    {
        double e = 0;
        int trues = 0;
        int falses = 0;
        double pt = 0;
        double pf = 0;
        for (String row : dataSet)
        {
            String[] data = row.split(",");
            if (data[targetCol].equalsIgnoreCase("0")) falses++;
            if (data[targetCol].equalsIgnoreCase("1")) trues++;
        }

        pt = (double)(trues) / (trues + falses);
        pf = (double)(falses) / (trues + falses);

        double positivePart = pt * (Math.log(pt) / Math.log(2));
        double negativePart = pf * (Math.log(pf) / Math.log(2));
        if (Double.isNaN(positivePart)) positivePart = 0;
        if (Double.isNaN(negativePart)) negativePart = 0;

        e = -1 * (positivePart + negativePart);
        return e;
    }

    private static void calculateEntropyForAllAttributes(int cols)
    {
        for (int i = 0; i < cols; i++)
        {
            entropy[i] = calculateEntropy(i, recordList);
            //System.out.println(entropy[i]);
        }
    }

    private static double calculateInformationGain(int col, ArrayList<String> dataSet)
    {
        int trues = 0;
        int falses = 0;
        double pt = 0;
        double pf = 0;
        double informationGain = 0;

        ArrayList<String> positiveList = new ArrayList<String>();
        ArrayList<String> negativeList = new ArrayList<String>();

        for (String row : dataSet)
        {
            String[] data = row.split(",");
            if (data[col].equalsIgnoreCase("0"))
            {
                falses++;
                negativeList.add(row);
            }
            if (data[col].equalsIgnoreCase("1"))
            {
                trues++;
                positiveList.add(row);
            }
        }

        pt = (double)(trues) / (trues + falses);
        pf = (double)(falses) / (trues + falses);

        informationGain = entropy[col] - (pt * calculateEntropy(col, positiveList) + pf * calculateEntropy(col, negativeList));
        return informationGain;
    }

    private static void calculateInformationGainForAllAttributes(int cols)
    {
        for (int i = 0; i < cols; i++)
        {
            informationGain[i] = calculateInformationGain(i, recordList);
            //System.out.println(informationGain[i]);
        }
    }

    private static void determineRootNode()
    {
        int attribute = 0;

        for (int i  = 0; i < numOfCols; i++)
        {
            if (informationGain[i] > attribute) attribute = i;
        }

        //rootNode = new Node(labels.get(attribute), recordList);
        //rootNode = train(recordList, new Node(labels.get(attribute)), labels);
    }

    private static boolean dataIsPure(ArrayList<String> data)
    {
        int trues = 0;
        int falses = 0;

        for (String line : data)
        {
            String[] records = line.split(",");
            if (records[targetCol].equalsIgnoreCase("0")) falses++;
            if (records[targetCol].equalsIgnoreCase("1")) trues++;
        }

        if ((trues > 0 && falses == 0) || (falses > 0 && trues == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
