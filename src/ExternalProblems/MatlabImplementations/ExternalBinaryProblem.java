package ExternalProblems.MatlabImplementations;

import CommunicationManager.CommandManager;
import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.ISolutionEvaluation;
import ExternalProblems.Abstractions.AbstractExternalBinaryProblem;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.solution.BinarySolution;


public class ExternalBinaryProblem extends AbstractExternalBinaryProblem
{

    ISolutionEvaluation<BinarySolution> evaluator;

    public ExternalBinaryProblem(ISolutionEvaluation<BinarySolution> evaluator,
                                 int numberOfVariables, int numberOfObjectives, String problemName, int bitsPerVariable) throws IllegalAccessException, InstantiationException, MatlabInvocationException, MatlabConnectionException
    {
        super(bitsPerVariable, problemName);
        this.evaluator = evaluator;

        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(numberOfObjectives);
    }

    @Override
    protected int getBitsPerVariable(int var1)
    {
        return this.numberOfBits;
    }

    @Override
    public void evaluate(BinarySolution s)
    {
        double[] objectives = evaluator.getSolution(s);
        for (int i = 0; i < objectives.length; i++)
        {
            s.setObjective(i, objectives[i]);
        }

    }
}
