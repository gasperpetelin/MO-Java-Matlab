package ExternalProblems.Abstractions;


import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;

import java.util.List;

public abstract class AbstractExternalDoubleProblem extends AbstractGenericProblem<DoubleSolution> implements DoubleProblem
{
    private List<Double> lowerLimit;
    private List<Double> upperLimit;

    public AbstractExternalDoubleProblem(List<Double> lowerLimit, List<Double> upperLimit, String problemName)
    {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.setName(problemName);
    }

    public Double getUpperBound(int index)
    {
        return this.upperLimit.get(index);
    }

    public Double getLowerBound(int index)
    {
        return this.lowerLimit.get(index);
    }

    public DoubleSolution createSolution()
    {
        return new DefaultDoubleSolution(this);
    }
}
