package Problems.ProblemBuilders;


import ConnectionManager.Implementations.DoubleMatlabManager;
import Problems.ExternalDoubleProblem;
import Problems.Limit;

import java.util.List;

public class DoubleProblemBuilder extends AbstractProblemBuilder<DoubleMatlabManager, DoubleProblemBuilder>
{
    public ExternalDoubleProblem build()
    {
        this.manager.newObject(this.problemName, this.toMatlabCode());

        int numberOfVariables = manager.getNumberOfVriables();
        int numberOfObjectives = manager.getNumberOfObjectives();

        List<Limit> limits = manager.getLimits(numberOfVariables);

        return new ExternalDoubleProblem(manager, numberOfVariables, numberOfObjectives,  limits);
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
