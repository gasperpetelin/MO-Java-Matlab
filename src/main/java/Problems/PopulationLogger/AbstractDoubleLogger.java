package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDoubleLogger implements IPopulationLogger<DoubleSolution>
{
    protected int numberOfVariables;
    protected int numberOfObjectives;
    protected String fileTitle;
    protected String fileType;
    protected Integer front;
    protected IAlgorithmInfo data;

    protected final String generationNumber = "generationNumber";
    protected final String evaluationNumber = "evaluationNumber";
    protected final String frontNumber = "frontNumber";

    protected int count = 0;
    protected int generation = 1;


    public AbstractDoubleLogger(String fileName, String fileType, Integer front)
    {
        this.fileTitle = fileName;
        this.fileType = fileType;
        this.front = front;
    }


    @Override
    public void init(int numberOfVariables, int numberOfObjectives)
    {
        this.numberOfVariables = numberOfVariables;
        this.numberOfObjectives = numberOfObjectives;
    }

    protected String formatSolution(DoubleSolution s)
    {
        StringBuilder b = new StringBuilder();
        b.append(s.getAttribute(this.generationNumber) + "," +
                s.getAttribute(this.evaluationNumber) + "," +
                s.getAttribute(this.frontNumber) + ",");

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
        return b.toString();
    }

    protected String getFileName(Date date)
    {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        return this.fileTitle + "_" +this.data.getFileNameDetails() + "_" + df.format(date) +  "." + this.fileType;
    }

    @Override
    public void addHeaderInfo(IAlgorithmInfo info)
    {
        this.data = info;
    }

    protected List<List<DoubleSolution>> arrangeInFronts(List<DoubleSolution> solutions)
    {
        List<List<DoubleSolution>> generations = new ArrayList<>();
        for(int i = 1; i < this.generation; i++)
        {
            generations.add(new ArrayList<DoubleSolution>());
        }
        for(DoubleSolution ds:solutions)
        {
            int gen = (int)ds.getAttribute(this.generationNumber);
            generations.get(gen-1).add(ds);
        }
        return generations;
    }

    protected List<DoubleSolution> computeSolutionFront(List<List<DoubleSolution>> generations)
    {
        List<DoubleSolution> ls = new ArrayList<>();
        for (List<DoubleSolution> pop:generations)
        {
            DominanceRanking<DoubleSolution> ranking = new DominanceRanking<>();
            ranking.computeRanking(pop);

            for (int i = 0; i < ranking.getNumberOfSubfronts(); i++)
            {
                List<DoubleSolution> frontLs = ranking.getSubfront(i);
                for (DoubleSolution s : frontLs)
                {
                    s.setAttribute(frontNumber, i);

                    if(this.front==null)
                        ls.add(s);
                    else
                    {
                        if(this.front == i)
                            ls.add(s);
                    }
                }
            }
        }
        return ls;
    }

}
