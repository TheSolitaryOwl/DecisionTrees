import java.io.*;
import java.util.ArrayList;

public class UADecisionTree
{
    private static int targetCol = 0;
    private static int numOfCols = 0;
    private static int maxTreeDepth = 0;
    private static double minimumImpurity = 0;
    private static ArrayList<String> recordList = new ArrayList<String>();
    private static double entropy[];
    private static double informationGain[];

    public static void main(String[] args) throws IOException
    {
        getTrainingMatrix("titanicdata.csv", "SURVIVED");
        setTreeMaxDepth(10);
        calculateEntropyForAllAttributes(numOfCols);
        calculateInformationGainForEachAttribute();
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

    private static void calulateEntropy(int col)
    {
        double e = 0;
        int trues = 0;
        int falses = 0;
        double pt = 0;
        double pf = 0;
        for (String row : recordList)
        {
            String[] data = row.split(",");
            if (data[col].equalsIgnoreCase("0")) falses++;
            if (data[col].equalsIgnoreCase("1")) trues++;
        }

        pt = (double)(trues) / (trues + falses);
        pf = (double)(falses) / (trues + falses);

        e = -1 * (pt * (Math.log(pt) / Math.log(2)) + pf * (Math.log(pf) / Math.log(2)));
        System.out.println(e);
        entropy[col] = e;
    }

    private static void calculateEntropyForAllAttributes(int cols)
    {
        for (int i = 0; i < cols; i++)
        {
            calulateEntropy(i);
            System.out.println(entropy[i]);
        }
    }

    private static void calculateInformationGainForEachAttribute()
    {
        
    }
}
