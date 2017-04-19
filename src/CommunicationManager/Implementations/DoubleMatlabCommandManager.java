package CommunicationManager.Implementations;


import CommunicationManager.CommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import matlabcontrol.MatlabConnectionException;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DoubleMatlabCommandManager extends CommandManager implements IConstrainEvaluation<DoubleSolution>
{
    public static DoubleMatlabCommandManager newInstance()
    {
        try
        {
            return new DoubleMatlabCommandManager();
        }
        catch (MatlabConnectionException ex)
        {
            throw new JMetalException("Failed Command Manager creation");
        }

    }

    private DoubleMatlabCommandManager() throws MatlabConnectionException
    {
        super();
    }

    private  String formatSolution(DoubleSolution solution)
    {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < solution.getNumberOfVariables(); i++)
        {
            b.append(solution.getVariableValueString(i)+";");
        }
        b.append("]");
        return b.toString();
    }


    @Override
    public double[] getSolution(DoubleSolution solution)
    {
        String args = formatSolution(solution);
        return this.getSolution(this.nameOfObjectVariable, args, solution.getNumberOfObjectives());
    }

    @Override
    public void sessionOpen()
    {
        this.openSession();
    }

    @Override
    public void sessionClose()
    {
        this.closeSession();
    }

    @Override
    public ConstraintEvaluation getConstraint(DoubleSolution solution)
    {
        String args = formatSolution(solution);
        return this.getConstraint(args);
    }

    @Override
    public int getNumberOfBits()
    {
        throw  new NotImplementedException();
    }

    @Override
    public void setBitsPerVariableFieldName(String name)
    {
        throw  new NotImplementedException();
    }
}
