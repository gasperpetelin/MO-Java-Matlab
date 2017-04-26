package Problems;


import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ExternalDoubleProblem extends AbstractDoubleProblem
{
    private String fileName = "testfile.txt";

    ISolutionEvaluation<DoubleSolution> evaluator;

    public ExternalDoubleProblem(ISolutionEvaluation<DoubleSolution> evaluator, String problemName, int numberOfVariables, int numberOfObjectives, List<Limit> limits)
    {
        this.evaluator = evaluator;

        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);
        this.setName(problemName);

        List<Double> lowerLimit = new ArrayList(this.getNumberOfVariables());
        List<Double> upperLimit = new ArrayList(this.getNumberOfVariables());

        for(int i = 0; i < this.getNumberOfVariables(); ++i) {
            lowerLimit.add(limits.get(i).getLower());
            upperLimit.add(limits.get(i).getUpper());
        }

        this.setLowerLimit(lowerLimit);
        this.setUpperLimit(upperLimit);
    }

    private void writeToFile(DoubleSolution solution, double[] objectives)
    {

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < solution.getNumberOfVariables(); i++)
        {
            b.append(solution.getVariableValueString(i) + ",");
        }
        for (int i = 0; i < objectives.length; i++)
        {
            b.append(objectives[i] + ",");
        }
        b.deleteCharAt(b.length()-1);
        b.append(System.lineSeparator());

        try
        {
            Files.write(Paths.get(fileName), b.toString().getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e)
        {

        }
    }

    @Override
    public void evaluate(DoubleSolution solution)
    {
        double[] objectives = evaluator.getSolution(solution);
        if(objectives.length<this.getNumberOfObjectives())
            throw new JMetalException("Evaluation function should return at least " + this.getNumberOfObjectives() + " objectives.");

        this.writeToFile(solution, objectives);

        for (int i = 0; i < this.getNumberOfObjectives(); i++)
        {
            solution.setObjective(i, objectives[i]);
        }
    }
}
