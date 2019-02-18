import java.io.*;

public class UADecisionTree
{
    private static int targetCol = 0;

    public static void main(String[] args) throws IOException
    {
        getTrainingMatrix("titanicdata.csv", "SURVIVED");
        System.out.println(targetCol);
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

            if (lineNum == 0) // get column with target label.
            {
                for (int i = 0; i < record.length; i++)
                {
                    if (record[i].equalsIgnoreCase(targetLabel))
                    {
                        targetCol = i;
                    }
                }
            }
        }

        bufferedReader.close();
    }

    public static void setTreeMaxDepth (int maxDepth)
    {

    }

    public static void setMinimumImpurity (float minimumImpurity)
    {

    }

    public static void train(String trainingDataFilename)
    {

    }

    public static void classifyValue (String record)
    {

    }
}
