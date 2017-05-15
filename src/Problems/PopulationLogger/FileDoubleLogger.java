package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FileDoubleLogger extends AbstractDoubleLogger
{
    boolean deleteFile = true;

    List<DoubleSolution> solutions = new ArrayList<>();

    public FileDoubleLogger(String fileName, String fileType)
    {
        super(fileName, fileType, null);
    }

    public FileDoubleLogger(String fileName, String fileType, Integer front)
    {
        super(fileName, fileType, front);
    }

    private void deleteFile(Date date)
    {
        if(this.deleteFile)
        {
            File file = new File(getFileName(date));
            file.delete();
            this.deleteFile = false;
        }
    }



    @Override
    public void logSolution(DoubleSolution solution)
    {
        this.count++;
        this.solutions.add(solution);

        solution.setAttribute(this.generationNumber, this.generation);
        solution.setAttribute(this.evaluationNumber, this.count);

        if(this.data==null || this.count%this.data.getPopulationSize()==0)
        {
            this.count=0;
            this.generation++;
        }
    }

    @Override
    public void save()
    {
        Date date = Calendar.getInstance().getTime();
        this.deleteFile(date);


        DominanceRanking<DoubleSolution> ranking = new DominanceRanking<>();
        ranking.computeRanking(this.solutions);


        for (int i = 0; i < ranking.getNumberOfSubfronts(); i++)
        {
            List<DoubleSolution> frontLs = ranking.getSubfront(i);
            for (DoubleSolution s : frontLs)
            {
                s.setAttribute(frontNumber, i);
            }
        }

        List<DoubleSolution> ls;
        if(this.front != null)
        {
            ls = ranking.getSubfront(this.front);
        }
        else
        {
            ls = this.solutions;
        }

        StringBuilder b = new StringBuilder();
        for(DoubleSolution s : ls)
        {
            b.append(this.formatSolution(s));
        }

        StringBuilder headerBuilder = new StringBuilder(this.numberOfVariables + "," +
                this.numberOfObjectives + "," + date);

        if(this.data!=null)
        {
            headerBuilder.append("," + this.data.getHeaderDetails());
        }

        try
        {
            FileWriter fstream = new FileWriter(getFileName(date),true);
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
}
