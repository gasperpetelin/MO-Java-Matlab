package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import ExternalProblems.Abstractions.AbstractExternalGenericProblem;
import ExternalProblems.MatlabImplementations.ConstrainedExternalDoubleProblem;
import ExternalProblems.MatlabImplementations.ExternalDoubleProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractDoubleProblemBuilder;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

public class MatlabConstrainedDoubleProblemBuilder extends MatlabAbstractDoubleProblemBuilder<MatlabConstrainedDoubleProblemBuilder>
{
    List<Double> lowerLimitConstraint = new ArrayList();
    List<Double> upperLimitConstraint = new ArrayList();
    boolean BoundsSetProgrammatically = false;

    public MatlabConstrainedDoubleProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }

    private List<Double> addDefaultBound(int numberofVariables, double limit)
    {
        List<Double> lowerLimitCo = new ArrayList();
        for (int i = 0; i < numberofVariables; ++i)
        {
            lowerLimitCo.add(limit);
        }
        return lowerLimitCo;
    }

    private void handleMissingLimits(int numberofVariables)
    {
        System.err.println("Number of limits should be same as number of variables. limits (-100, 100) will be used");
        lowerLimitConstraint = this.addDefaultBound(numberofVariables, -100.0);
        upperLimitConstraint = this.addDefaultBound(numberofVariables, +100.0);
    }


    public MatlabConstrainedDoubleProblemBuilder setLimit(double lowerLimit, double upperLimit)
    {
        lowerLimitConstraint.add(lowerLimit);
        upperLimitConstraint.add(upperLimit);
        this.BoundsSetProgrammatically = true;
        return this;
    }

    @Override
    public AbstractExternalGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException, JMException
    {
        manager.openSession();
        manager.setProblemPath(this.problemPath);
        manager.newObject(nameOfCreatedVariable, problemName, this.buildConstructorArguments());

        int numberofVariables = manager.getVariable(nameOfCreatedVariable + "." + numberOfVariablesDefaultField, IntFunctionArgument.class).getValue();
        int numberofObjectives = manager.getVariable(nameOfCreatedVariable + "." + numberOfObjectivesDefaultField, IntFunctionArgument.class).getValue();


        if(!this.BoundsSetProgrammatically)
        {
            double[][] limits = manager.get2DArray(nameOfCreatedVariable + ".Limits");
            if (limits==null || limits.length != numberofVariables)
            {
                this.handleMissingLimits(numberofVariables);
            }
            else
            {
                for (int i = 0; i < numberofVariables; ++i)
                {
                    this.setLimit(limits[0][0], limits[0][1]);
                }
            }
        }
        else
        {
            if(lowerLimitConstraint.size() != numberofVariables)
            {
                this.handleMissingLimits(numberofVariables);
            }
        }

        return new ConstrainedExternalDoubleProblem(manager, nameOfCreatedVariable,
                numberofVariables, numberofObjectives, lowerLimitConstraint, upperLimitConstraint);
    }

}
