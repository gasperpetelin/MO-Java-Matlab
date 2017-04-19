package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import CommunicationManager.Implementations.DoubleMatlabCommandManager;
import CommunicationManager.Implementations.Limit;
import ExternalProblems.MatlabImplementations.ConstrainedExternalDoubleProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractDoubleProblemBuilder;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.DoubleSolution;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

public class MatlabConstrainedDoubleProblemBuilder extends MatlabAbstractDoubleProblemBuilder<MatlabConstrainedDoubleProblemBuilder>
{
    IConstrainEvaluation<DoubleSolution> evaluator;

    public MatlabConstrainedDoubleProblemBuilder(String problemName, ICommandManager manager, IConstrainEvaluation<DoubleSolution> evaluator)
    {
        super(problemName, manager);
        this.evaluator = evaluator;
    }

    public MatlabConstrainedDoubleProblemBuilder(String problemName, DoubleMatlabCommandManager manager)
    {
        this(problemName, manager, manager);
    }

    public MatlabConstrainedDoubleProblemBuilder setLimit(double lowerLimit, double upperLimit)
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

        return new ConstrainedExternalDoubleProblem(evaluator, manager.getProblemName(),
                this.getNumberOfVariables(), this.getNumberOfObjectives(), lowerLimitConstraint, upperLimitConstraint);
    }

}
