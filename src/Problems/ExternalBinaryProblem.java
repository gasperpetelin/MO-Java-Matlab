package Problems;

import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

public class ExternalBinaryProblem extends AbstractBinaryProblem
{
    ISolutionEvaluation<BinarySolution> evaluator;
    int bitsPerVariable;

    public ExternalBinaryProblem(ISolutionEvaluation<BinarySolution> evaluator, int numberOfVariables, int numberOfObjectives, int bitsPerVariable)
    {
        this.evaluator = evaluator;
        this.bitsPerVariable = bitsPerVariable;
        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);
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
        for (int i = 0; i < objectives.length; i++)
        {
            solution.setObjective(i, objectives[i]);
        }
    }
}
