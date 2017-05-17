package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FileDoubleRealTimeLogger extends AbstractDoubleLogger
{
    public FileDoubleRealTimeLogger(String fileName, String fileType)
    {
        super(fileName, fileType, null);
    }

    public FileDoubleRealTimeLogger(String fileName, String fileType, Integer front)
    {
        super(fileName, fileType, front);
    }

    Date beginWriteDate = null;
    boolean writeHeader = true;
    String fullFileName = null;

    @Override
    public void logSolution(DoubleSolution solution)
    {
        this.count++;

        solution.setAttribute(this.generationNumber, this.generation);
        solution.setAttribute(this.evaluationNumber, this.count);


        try
        {
            if(beginWriteDate==null)
            {
                beginWriteDate = Calendar.getInstance().getTime();
            }
            this.fullFileName = getFileName(beginWriteDate);
            FileWriter fstream = new FileWriter(fullFileName,true);
            BufferedWriter fbw = new BufferedWriter(fstream);


            if(writeHeader)
            {
                StringBuilder headerBuilder = new StringBuilder(this.numberOfVariables + "," +
                        this.numberOfObjectives + "," + this.beginWriteDate);

                if(this.data!=null)
                {
                    headerBuilder.append("," + this.data.getHeaderDetails());
                }
                fbw.write(headerBuilder.toString());
                fbw.newLine();
                this.writeHeader = false;
            }


            fbw.write(this.formatSolution(solution));
            fbw.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        if(this.data==null || this.count%this.data.getPopulationSize()==0)
        {




            this.count=0;
            this.generation++;
        }
    }

    private List<DoubleSolution> getSolutionsFromFile()
    {
        int numberOfVariables = 0;
        int numberOfObjectives = 0;
        boolean header = true;
        List<DoubleSolution> ls = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.fullFileName))) {
            String line;
            while((line = br.readLine()) != null)
            {
                if(header)
                {
                    String[] arr = line.split(",");
                    numberOfVariables = Integer.parseInt(arr[0]);
                    numberOfObjectives = Integer.parseInt(arr[1]);
                    header = false;
                }
                else
                {
                    String[] arr = line.split(",");
                    double[]vars = new double[numberOfVariables];
                    double[]obj = new double[numberOfObjectives];
                    for (int i = 0; i < numberOfVariables; i++)
                    {
                        vars[i] = Double.parseDouble(arr[i+3]);
                    }
                    for (int i = 0; i < numberOfObjectives; i++)
                    {
                        obj[i] = Double.parseDouble(arr[i+3+numberOfVariables]);
                    }
                    DoubleSolution s = new SolutionSort(vars, obj);
                    s.setAttribute(generationNumber, Integer.parseInt(arr[0]));
                    s.setAttribute(evaluationNumber, Integer.parseInt(arr[1]));
                    ls.add(s);
                }
            }
        } catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return ls;
    }



    private void updateFronts()
    {

    }

    @Override
    public void save()
    {
        List<DoubleSolution> ls = this.getSolutionsFromFile();
        List<List<DoubleSolution>> generations =  this.arrangeInFronts(ls);
        List<DoubleSolution> lsFronts = this.computeSolutionFront(generations);

        this.deleteFile(this.beginWriteDate);

        StringBuilder b = new StringBuilder();
        for(DoubleSolution s : lsFronts)
        {
            b.append(this.formatSolution(s));
        }

        StringBuilder headerBuilder = new StringBuilder(this.numberOfVariables + "," +
                this.numberOfObjectives + "," + beginWriteDate);

        if(this.data!=null)
        {
            headerBuilder.append("," + this.data.getHeaderDetails());
        }

        try
        {
            FileWriter fstream = new FileWriter(getFileName(beginWriteDate),true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(headerBuilder.toString());
            fbw.newLine();
            fbw.write(b.toString());
            fbw.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteFile(Date date)
    {
        File file = new File(getFileName(date));
        file.delete();
    }
}
