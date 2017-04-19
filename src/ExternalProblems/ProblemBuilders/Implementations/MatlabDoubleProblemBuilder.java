package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.ISolutionEvaluation;
import CommunicationManager.Implementations.DoubleMatlabCommandManager;
import CommunicationManager.Implementations.Limit;
import ExternalProblems.MatlabImplementations.ExternalDoubleProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractDoubleProblemBuilder;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.DoubleSolution;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

public class MatlabDoubleProblemBuilder extends MatlabAbstractDoubleProblemBuilder<MatlabDoubleProblemBuilder>
{
    ISolutionEvaluation<DoubleSolution> evaluator;

    public MatlabDoubleProblemBuilder(String problemName, ICommandManager manager, ISolutionEvaluation<DoubleSolution> evaluator)
    {
        super(problemName, manager);
        this.evaluator = evaluator;
    }

    public MatlabDoubleProblemBuilder(String problemName, DoubleMatlabCommandManager manager)
    {
        this(problemName, manager, manager);
    }

    public MatlabDoubleProblemBuilder(String problemName)
    {
        this(problemName, DoubleMatlabCommandManager.newInstance());
    }

    public MatlabDoubleProblemBuilder setLimit(double lowerLimit, double upperLimit)
    {
        lowerLimitConstraint.add(lowerLimit);
        upperLimitConstraint.add(upperLimit);
        this.BoundsSetProgrammatically = true;
        return this;
    }

    @Override
    public AbstractGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException, JMException
    {
        manager.setProblemPath(this.problemPath);
        manager.newObject(problemName, this.buildConstructorArguments());

        this.numberofVariables = this.getNumberOfVariables();
        LimitsCheck(this.numberofVariables);

        return new ExternalDoubleProblem(evaluator, manager.getProblemName(),
                this.getNumberOfVariables(), this.getNumberOfObjectives(), lowerLimitConstraint, upperLimitConstraint);
    }
}
