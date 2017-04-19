package ExternalProblems.MatlabImplementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.Implementations.ConstraintEvaluation;
import CommunicationManager.CommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import ExternalProblems.Abstractions.AbstractExternalDoubleProblem;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.List;

public class ConstrainedExternalDoubleProblem extends AbstractExternalDoubleProblem implements ConstrainedProblem<DoubleSolution>
{

    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree = new OverallConstraintViolation();
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints = new NumberOfViolatedConstraints();

    private IConstrainEvaluation<DoubleSolution> evaluator;

    public ConstrainedExternalDoubleProblem(IConstrainEvaluation<DoubleSolution> evaluator,
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

        this.setNumberOfConstraints(2);
    }

    @Override
    public void evaluate(DoubleSolution solution)
    {
        double[] ev = evaluator.getSolution(solution);
        for (int i = 0; i < ev.length; i++)
        {
            solution.setObjective(i, ev[i]);
        }
    }

    @Override
    public void evaluateConstraints(DoubleSolution solution)
    {
        ConstraintEvaluation ev = evaluator.getConstraint(solution);
        this.overallConstraintViolationDegree.setAttribute(solution, ev.getOverallConstraintViolationDegree());
        this.numberOfViolatedConstraints.setAttribute(solution, ev.getNumberOfViolatedConstraints());
    }
}
