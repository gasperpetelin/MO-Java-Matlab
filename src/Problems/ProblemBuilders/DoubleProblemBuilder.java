package Problems.ProblemBuilders;


import ConnectionManager.Implementations.DoubleMatlabManager;
import Problems.ExternalDoubleProblem;
import Problems.Limit;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;

public class DoubleProblemBuilder extends AbstractProblemBuilder<DoubleMatlabManager, DoubleProblemBuilder>
{

    List<Limit> limits;

    public ExternalDoubleProblem build()
    {
        this.manager.newObject(this.problemName, this.toMatlabCode());
        if(this.overriddenNumberOfVariables<1)
        {
            this.overriddenNumberOfVariables =  manager.getNumberOfVariables();
        }
        if(this.overriddenNumberOfObjectives<1)
        {
            this.overriddenNumberOfObjectives =  manager.getNumberOfObjectives();
        }
        if(this.overriddenProblemName.equals(""))
        {
            this.overriddenProblemName =  manager.getProblemName();
        }

        if(this.limits==null)
            this.limits = manager.getLimits();

        if(this.limits.size()<this.overriddenNumberOfVariables)
            throw new JMetalException("Number of variables should match number of limits.");

        return new ExternalDoubleProblem(manager, this.overriddenProblemName, this.overriddenNumberOfVariables, this.overriddenNumberOfObjectives,  limits);
    }

    public DoubleProblemBuilder addLimits(List<Limit> limits)
    {
        this.limits = limits;
        return this;
    }

    public DoubleProblemBuilder addLimit(double lower, double upper)
    {
        return addLimit(new Limit(lower, upper));
    }

    public DoubleProblemBuilder addLimit(Limit limit)
    {
        if(this.limits==null)
        {
            this.limits = new ArrayList<>();
        }
        this.limits.add(limit);
        return this;

    }

    public DoubleProblemBuilder(DoubleMatlabManager manager, String problemName)
    {
        this.manager = manager;
        this.problemName = problemName;
    }

    public FluentArrayBuilder<DoubleProblemBuilder> startArray()
    {
        return new FluentArrayBuilder<>(this);
    }




}
