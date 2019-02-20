import java.io.*;
import java.util.ArrayList;

public class UADecisionTree
{
    private static int targetCol = 0;
    private static int numOfCols = 0;
    private static int maxTreeDepth = 0;
    private static double minimumImpurity = 0;
    private static ArrayList<String> recordList = new ArrayList<String>();
    private static String[] labels;
    private static double entropy[];
    private static double informationGain[];
    Node rootNode = null;

    public static void main(String[] args) throws IOException
    {
        getTrainingMatrix("titanicdata.csv", "SURVIVED");
        setTreeMaxDepth(10);
        calculateEntropyForAllAttributes(numOfCols);
        calculateInformationGainForAllAttributes(numOfCols);
        determineRootNode();
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
                labels = record.clone();
                // FOR STORING VALUES FOR EACH ATTRIBUTE
                informationGain = new double[record.length];
                entropy = new double[record.length];
                numOfCols = record.length;

                for (int i = 0; i < record.length; i++)
                {
                    if (record[i].equalsIgnoreCase(targetLabel))
                    {
                        targetCol = i;
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

    public static void train(String trainingDataFilename)
    {

    }

    public static void classifyValue (String record)
    {

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

    private static double calculateInformationGain(int col)
    {
        int trues = 0;
        int falses = 0;
        double pt = 0;
        double pf = 0;
        double informationGain = 0;

        ArrayList<String> positiveList = new ArrayList<String>();
        ArrayList<String> negativeList = new ArrayList<String>();

        for (String row : recordList)
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
            informationGain[i] = calculateInformationGain(i);
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

        System.out.println(labels[attribute]);
    }
}
