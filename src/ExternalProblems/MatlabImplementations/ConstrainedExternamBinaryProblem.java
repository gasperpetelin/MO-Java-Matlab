package ExternalProblems.MatlabImplementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.ISolutionEvaluation;
import CommunicationManager.Implementations.BinaryMatlabCommandManager;
import CommunicationManager.Implementations.ConstraintEvaluation;
import CommunicationManager.CommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import ExternalProblems.Abstractions.AbstractExternalBinaryProblem;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

public class ConstrainedExternamBinaryProblem extends AbstractExternalBinaryProblem implements ConstrainedProblem<BinarySolution>
{

    public OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree ;
    public NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints ;

    IConstrainEvaluation<BinarySolution> evaluator;

    public ConstrainedExternamBinaryProblem(IConstrainEvaluation<BinarySolution> evaluator,
           int numberOfVariables, int numberOfObjectives, String problemName, int numberOfBits) throws IllegalAccessException, InstantiationException, MatlabInvocationException, MatlabConnectionException
    {
        super(numberOfBits, problemName);

        this.evaluator = evaluator;
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(numberOfObjectives);

        overallConstraintViolationDegree = new OverallConstraintViolation<>() ;
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<>() ;
    }

    @Override
    protected int getBitsPerVariable(int var1)
    {
        return this.numberOfBits;
    }

    @Override
    public void evaluate(BinarySolution s)
    {
        double[] ev = evaluator.getSolution(s);
        for (int i = 0; i < ev.length; i++)
        {
            s.setObjective(i, ev[i]);
        }
    }

    @Override
    public void evaluateConstraints(BinarySolution s)
    {
        ConstraintEvaluation ev =  evaluator.getConstraint(s);
        this.overallConstraintViolationDegree.setAttribute(s, ev.getOverallConstraintViolationDegree());
        this.numberOfViolatedConstraints.setAttribute(s, ev.getNumberOfViolatedConstraints());
    }
}
