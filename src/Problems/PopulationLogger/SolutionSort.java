package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class SolutionSort implements DoubleSolution
{

    double[] variables;
    double[] objectives;
    public SolutionSort(double[] variables, double[] objectives)
    {
        this.variables = variables;
        this.objectives = objectives;
    }

    @Override
    public Double getLowerBound(int index)
    {
        throw new NotImplementedException();
    }

    @Override
    public Double getUpperBound(int index)
    {
        throw new NotImplementedException();
    }

    @Override
    public void setObjective(int index, double value)
    {
        this.objectives[index] = value;
    }

    @Override
    public double getObjective(int index)
    {
        return this.objectives[index];
    }

    @Override
    public Double getVariableValue(int index)
    {
        return this.variables[index];
    }

    @Override
    public void setVariableValue(int index, Double value)
    {
        this.variables[index] = value;
    }

    @Override
    public String getVariableValueString(int index)
    {
        return Double.toString(this.variables[index]);
    }

    @Override
    public int getNumberOfVariables()
    {
        return this.variables.length;
    }

    @Override
    public int getNumberOfObjectives()
    {
        return this.objectives.length;
    }

    @Override
    public Solution<Double> copy()
    {
        throw new NotImplementedException();
    }

    Map<Object, Object> map = new HashMap<>();

    @Override
    public void setAttribute(Object id, Object value)
    {
        map.put(id, value);
    }

    @Override
    public Object getAttribute(Object id)
    {
        return map.get(id);
    }
}
