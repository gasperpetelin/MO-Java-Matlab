package Problems;

import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;

public class ExternalBinaryProblem extends AbstractBinaryProblem
{
    ISolutionEvaluation<BinarySolution> evaluator;
    int bitsPerVariable;

    public ExternalBinaryProblem(ISolutionEvaluation<BinarySolution> evaluator, String problemName , int numberOfVariables, int numberOfObjectives, int bitsPerVariable)
    {
        this.evaluator = evaluator;
        this.bitsPerVariable = bitsPerVariable;
        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);
        this.setName(problemName);
    }

    @Override
    protected int getBitsPerVariable(int index)
    {
        return this.bitsPerVariable;
    }

    @Override
    public void evaluate(BinarySolution solution)
    {
        double[] objectives = evaluator.getSolution(solution);
        if(objectives.length<this.getNumberOfObjectives())
            throw new JMetalException("Evaluation function should return at least " + this.getNumberOfObjectives() + " objectives.");
        for (int i = 0; i < objectives.length; i++)
        {
            solution.setObjective(i, objectives[i]);
        }
    }
}
