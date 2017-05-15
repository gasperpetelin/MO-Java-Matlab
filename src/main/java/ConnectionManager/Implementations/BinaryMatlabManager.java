package ConnectionManager.Implementations;

import ConnectionManager.ManagerConfigs.Implementations.BinarySolutionMatlabManagerConfig;
import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import ConnectionManager.MatlabManager;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;


public class BinaryMatlabManager extends MatlabManager<BinarySolutionMatlabManagerConfig> implements ISolutionEvaluation<BinarySolution>
{
    public BinaryMatlabManager(BinarySolutionMatlabManagerConfig config)
    {
        super(config);
    }

    public BinaryMatlabManager()
    {
        super(new BinarySolutionMatlabManagerConfig());
    }

    public int getBitsPerVariable()
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getNumberOfBitsPerVariable();
            Object var = this.proxy.getVariable(command);
            return (int) ((double[])var)[0];
        }
        catch (MatlabInvocationException e)
        {
            throw new JMetalException("Unable to get number of bits per variable", e);
        }
    }

    private String toMatlabStructure(BinarySolution solution)
    {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < solution.getNumberOfVariables(); i++)
        {
            BinarySet c = solution.getVariableValue(i);
            for (int j = 0; j < c.getBinarySetLength(); j++)
            {
                b.append( (c.get(j)? 1 : 0) + ",");
            }
            b.deleteCharAt(b.length()-1);
            b.append(";");
        }
        b.deleteCharAt(b.length()-1);
        b.append("]");

        return b.toString();
    }

    @Override
    public double[] getSolution(BinarySolution solution)
    {
        try
        {
            String command = this.config.getVariableName()
                    + "." + this.config.getEvaluateMethod() + "(" + this.toMatlabStructure(solution) + ")";

            return (double[]) this.proxy.returningEval(command, 1)[0];

        }
        catch (MatlabInvocationException e)
        {
            e.printStackTrace();
        }
        return new double[solution.getNumberOfObjectives()];

    }
}
