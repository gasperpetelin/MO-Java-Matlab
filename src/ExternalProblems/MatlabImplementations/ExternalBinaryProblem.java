package ExternalProblems.MatlabImplementations;

import CommunicationManager.ICommandManager;
import ExternalProblems.Abstractions.AbstractExternalBinaryProblem;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.ArrayFunctionArgument;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;

import java.util.List;


public class ExternalBinaryProblem extends AbstractExternalBinaryProblem
{


    public ExternalBinaryProblem(ICommandManager manager, String matlabVariableName,
           int numberOfVariables, int numberOfObjectives, String problemName, String bitsPerVariableName) throws IllegalAccessException, InstantiationException, MatlabInvocationException, MatlabConnectionException
    {
        super(manager, matlabVariableName);
        this.numberOfBits = manager.getVariable(matlabVariableName + "." + bitsPerVariableName, IntFunctionArgument.class).getValue();


        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(numberOfObjectives);
        setName(problemName);
    }

    @Override
    protected int getBitsPerVariable(int var1)
    {
        return this.numberOfBits;
    }

    @Override
    public void evaluate(BinarySolution s)
    {
        String functionArgument = MatlabMatrixBuilder(s);
        String command = this.nameOfObjectVariable + ".evaluate(" + functionArgument + ")";

        try
        {
            manager.executeCommand("testV =" +command+";");
            List<AbstractMatlabVariables> af = manager.getVariable("testV", ArrayFunctionArgument.class).getValue();
            for (int i = 0; i < s.getNumberOfObjectives(); i++)
            {
                DoubleFunctionArgument a = (DoubleFunctionArgument)af.get(i);
                s.setObjective(i, a.getValue());
            }

        }
        catch (Exception ex)
        {
            JMetalException newex = new JMetalException("Error with session.", ex);
            throw newex;
        }
    }
}
