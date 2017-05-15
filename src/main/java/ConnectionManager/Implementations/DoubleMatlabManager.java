package ConnectionManager.Implementations;

import ConnectionManager.ManagerConfigs.Implementations.DoubleSolutionMatlabManagerConfig;
import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import ConnectionManager.MatlabManager;
import Problems.Limit;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class DoubleMatlabManager extends MatlabManager<DoubleSolutionMatlabManagerConfig> implements ISolutionEvaluation<DoubleSolution>
{
    public DoubleMatlabManager(DoubleSolutionMatlabManagerConfig config)
    {
        super(config);
    }

    public DoubleMatlabManager()
    {
        super(new DoubleSolutionMatlabManagerConfig());
    }

    public List<Limit> getLimits()
    {
        List<Limit> array = new ArrayList<>();
        double[][] limits = this.get2DArray(this.config.getVariableName() + "." + this.config.getVariableLimits());
        if (limits==null)
            return null;
        for (int i = 0; i < limits.length; ++i)
        {
            array.add(new Limit(limits[i][0], limits[i][1]));
        }
        return array;
    }


    private String toMatlabStructure(DoubleSolution solution)
    {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < solution.getNumberOfVariables(); i++)
        {
            b.append(solution.getVariableValueString(i)+",");
        }
        b.deleteCharAt(b.length()-1);
        b.append("]");
        return b.toString();
    }

    @Override
    public double[] getSolution(DoubleSolution solution)
    {
        try
        {
            String command = this.config.getVariableName()
                    + "." + this.config.getEvaluateMethod() + "(" + this.toMatlabStructure(solution) + ")";

            return  (double[]) this.proxy.returningEval(command, 1)[0];

        }
        catch (MatlabInvocationException e)
        {
            e.printStackTrace();
        }
        return new double[solution.getNumberOfObjectives()];
    }
}
