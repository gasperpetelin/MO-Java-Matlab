package Problems;


import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class ExternalDoubleProblem extends AbstractDoubleProblem
{
    ISolutionEvaluation<DoubleSolution> evaluator;

    public ExternalDoubleProblem(ISolutionEvaluation<DoubleSolution> evaluator, int numberOfVariables, int numberOfObjectives, List<Limit> limits)
    {
        this.evaluator = evaluator;

        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);

        List<Double> lowerLimit = new ArrayList(this.getNumberOfVariables());
        List<Double> upperLimit = new ArrayList(this.getNumberOfVariables());

        for(int i = 0; i < this.getNumberOfVariables(); ++i) {
            lowerLimit.add(limits.get(i).getLower());
            upperLimit.add(limits.get(i).getUpper());
        }

        this.setLowerLimit(lowerLimit);
        this.setUpperLimit(upperLimit);
    }

    @Override
    public void evaluate(DoubleSolution solution)
    {
        double[] objectives = evaluator.getSolution(solution);
        for (int i = 0; i < objectives.length; i++)
        {
            solution.setObjective(i, objectives[i]);
        }
    }
}
