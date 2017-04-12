package ExternalProblems.MatlabImplementations;


import CommunicationManager.ConstraintEvaluation;
import CommunicationManager.ICommandManager;
import ExternalProblems.Abstractions.AbstractExternalDoubleProblem;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.ArrayFunctionArgument;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.List;

public class ConstrainedExternalDoubleProblem extends AbstractExternalDoubleProblem implements ConstrainedProblem<DoubleSolution>
{

    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints;


    public ConstrainedExternalDoubleProblem(ICommandManager manager,
                                 String matlabVariableName,
                                 int numberOfVariables,
                                 int numberOfObjectives,
                                 List<Double> lowerLimits,
                                 List<Double> upperLimits)
    {
        super(manager, matlabVariableName);

        this.setName("ProblemName");
        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);
        this.setLowerLimit(lowerLimits);
        this.setUpperLimit(upperLimits);

        this.setNumberOfConstraints(2);

        this.overallConstraintViolationDegree = new OverallConstraintViolation();
        this.numberOfViolatedConstraints = new NumberOfViolatedConstraints();
    }

    private  String formatSolution(DoubleSolution solution)
    {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < this.getNumberOfVariables(); i++)
        {
            b.append(solution.getVariableValueString(i)+";");
        }
        b.append("]");
        return b.toString();
    }

    @Override
    public void evaluate(DoubleSolution solution)
    {
        String functionArgument = formatSolution(solution);
        String command = this.nameOfObjectVariable + ".evaluate(" + functionArgument + ")";
        try
        {
            manager.executeCommand("testV =" + command + ";");
            List<AbstractMatlabVariables> af = manager.getVariable("testV", ArrayFunctionArgument.class).getValue();
            for (int i = 0; i < solution.getNumberOfObjectives(); i++)
            {
                DoubleFunctionArgument a = (DoubleFunctionArgument) af.get(i);
                solution.setObjective(i, a.getValue());
            }
        }
        catch (Exception ex)
        {
            JMetalException newex = new JMetalException("Error with session.", ex);
            throw newex;
        }
    }

    @Override
    public void evaluateConstraints(DoubleSolution solution)
    {
        String functionArgument = formatSolution(solution);
        ConstraintEvaluation ev = manager.getConstraint(this.nameOfObjectVariable, "evaluateConstraints", functionArgument);
        this.overallConstraintViolationDegree.setAttribute(solution, ev.getOverallConstraintViolationDegree());
        this.numberOfViolatedConstraints.setAttribute(solution, ev.getNumberOfViolatedConstraints());
    }
}
