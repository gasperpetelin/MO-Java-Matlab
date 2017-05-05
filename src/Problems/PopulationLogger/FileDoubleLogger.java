package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;

public class FileDoubleLogger implements IPopulationLogger<DoubleSolution>
{

    final String generationNumber = "generationNumber";
    final String evaluationNumber = "evaluationNumber";

    String fileName;
    int numberOfVariables;
    int numberOfObjectives;
    boolean deleteFile = true;
    IAlgorithmInfo data;

    Integer front;

    List<DoubleSolution> solutions = new ArrayList<>();

    public FileDoubleLogger(String fileName)
    {
        this(fileName, null);
    }

    public FileDoubleLogger(String fileName, Integer front)
    {
        this.fileName = fileName;
        this.front = front;
    }

    @Override
    public void init(int numberOfVariables, int numberOfObjectives)
    {
        this.numberOfVariables = numberOfVariables;
        this.numberOfObjectives = numberOfObjectives;
        this.deleteFile();
    }

    private void deleteFile()
    {
        if(this.deleteFile)
        {
            File file = new File(this.fileName);
            file.delete();
            this.deleteFile = false;
        }
    }

    int count = 0;
    int generation = 1;

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
    public void addHeaderInfo(IAlgorithmInfo info)
    {
        this.data = info;
    }

    @Override
    public void save()
    {
        List<DoubleSolution> ls;
        if(this.front != null)
        {
            DominanceRanking<DoubleSolution> ranking = new DominanceRanking<>();
            ranking.computeRanking(this.solutions);
            ls = ranking.getSubfront(this.front);
        }
        else
        {
            ls = this.solutions;
        }

        StringBuilder b = new StringBuilder();
        for(DoubleSolution s : ls)
        {
            b.append(s.getAttribute(this.generationNumber) + "," + s.getAttribute(this.evaluationNumber) + ",");
            for (int i = 0; i < s.getNumberOfVariables(); i++)
            {
                b.append(s.getVariableValueString(i) + ",");
            }
            for (int i = 0; i < s.getNumberOfObjectives(); i++)
            {
                b.append(s.getObjective(i) + ",");
            }
            b.deleteCharAt(b.length()-1);
            b.append(System.lineSeparator());
        }

        StringBuilder headerBuilder = new StringBuilder(this.numberOfVariables + "," +
                this.numberOfObjectives + "," + LocalDateTime.now());

        if(this.data!=null)
        {
            headerBuilder.append("," + this.data.getHeaderDetails());
        }

        try
        {
            FileWriter fstream = new FileWriter(this.fileName,true);
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
