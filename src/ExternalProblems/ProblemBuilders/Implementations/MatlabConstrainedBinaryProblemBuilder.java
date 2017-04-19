package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.IConstrainEvaluation;
import CommunicationManager.Implementations.BinaryMatlabCommandManager;
import ExternalProblems.MatlabImplementations.ConstrainedExternamBinaryProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractBinaryProblemBuilder;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.BinarySolution;

import javax.management.JMException;

public class MatlabConstrainedBinaryProblemBuilder extends MatlabAbstractBinaryProblemBuilder<MatlabConstrainedBinaryProblemBuilder>
{
    IConstrainEvaluation<BinarySolution> evaluator;
    public MatlabConstrainedBinaryProblemBuilder(String problemName, ICommandManager manager, IConstrainEvaluation<BinarySolution> evaluator)
    {
        super(problemName, manager);
        this.evaluator = evaluator;
    }

    public MatlabConstrainedBinaryProblemBuilder(String problemName, BinaryMatlabCommandManager manager)
    {
        this(problemName, manager, manager);
    }

    public MatlabConstrainedBinaryProblemBuilder(String problemName)
    {
        this(problemName, BinaryMatlabCommandManager.newInstance());
    }

    @Override
    public AbstractGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException, JMException
    {
        manager.setProblemPath(this.problemPath);
        manager.newObject(problemName, this.buildConstructorArguments());

        int numberOfBits = manager.getNumberOfBits();

        return new ConstrainedExternamBinaryProblem(evaluator,
                this.getNumberOfVariables(), this.getNumberOfObjectives(),
                manager.getProblemName(), numberOfBits);
    }
}
