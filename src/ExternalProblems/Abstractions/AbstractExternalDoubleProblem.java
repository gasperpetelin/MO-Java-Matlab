package ExternalProblems.Abstractions;


import CommunicationManager.ICommandManager;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;

import java.util.List;

public abstract class AbstractExternalDoubleProblem extends AbstractExternalGenericProblem<DoubleSolution> implements DoubleProblem
{


    private List<Double> lowerLimit;
    private List<Double> upperLimit;

    public AbstractExternalDoubleProblem(ICommandManager manager, String nameOfObjectVariable)
    {
        super(manager, nameOfObjectVariable);
    }


    public Double getUpperBound(int index) {
        return (Double)this.upperLimit.get(index);
    }

    public Double getLowerBound(int index) {
        return (Double)this.lowerLimit.get(index);
    }

    protected void setLowerLimit(List<Double> lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    protected void setUpperLimit(List<Double> upperLimit) {
        this.upperLimit = upperLimit;
    }

    public DoubleSolution createSolution() {
        return new DefaultDoubleSolution(this);
    }
}
