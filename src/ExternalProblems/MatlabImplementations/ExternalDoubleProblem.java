package ExternalProblems.MatlabImplementations;


import CommunicationManager.IEvaluators.ISolutionEvaluation;
import ExternalProblems.Abstractions.AbstractExternalDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class ExternalDoubleProblem extends AbstractExternalDoubleProblem
{
    ISolutionEvaluation<DoubleSolution> evaluator;

    public ExternalDoubleProblem(ISolutionEvaluation<DoubleSolution> evaluator,
                                 String problemName,
                                 int numberOfVariables,
                                 int numberOfObjectives,
                                 List<Double> lowerLimits,
                                 List<Double> upperLimits)
    {
        super(lowerLimits, upperLimits, problemName);
        this.evaluator = evaluator;
        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);

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
