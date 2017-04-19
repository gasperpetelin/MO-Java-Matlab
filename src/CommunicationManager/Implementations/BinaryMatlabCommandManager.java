package CommunicationManager.Implementations;


import CommunicationManager.CommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;

public class BinaryMatlabCommandManager extends CommandManager implements IConstrainEvaluation<BinarySolution>
{
    protected String bitsPerVariableDefaultField = "BitsPerVariable";

    public void setBitsPerVariableFieldName(String name)
    {
        this.bitsPerVariableDefaultField  = name;
    }

    public int getNumberOfBits()
    {
        try
        {
            return this.getVariable(this.nameOfObjectVariable + "." + bitsPerVariableDefaultField, IntFunctionArgument.class).getValue();
        }
        catch (Exception e)
        {
            throw new JMetalException("Unable to get number of variables", e);
        }
    }

    public static BinaryMatlabCommandManager newInstance()
    {
        try
        {
            return new BinaryMatlabCommandManager();
        }
        catch (MatlabConnectionException ex)
        {
            throw new JMetalException("Failed Command Manager creation");
        }
    }

    private BinaryMatlabCommandManager() throws MatlabConnectionException
    {
        super();
    }

    protected String formatSolution(BinarySolution s)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < s.getNumberOfVariables(); i++)
        {
            builder.append("[" +s.getVariableValueString(i).replace("", " ").trim()+"];");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public double[] getSolution(BinarySolution solution)
    {
        String args = formatSolution(solution);
        return this.getSolution(this.nameOfObjectVariable, args, solution.getNumberOfObjectives());
    }

    @Override
    public void sessionOpen()
    {
        this.sessionOpen();
    }

    @Override
    public void sessionClose()
    {
        this.closeSession();
    }

    @Override
    public ConstraintEvaluation getConstraint(BinarySolution solution)
    {
        String args = formatSolution(solution);
        return this.getConstraint(args);
    }

}
