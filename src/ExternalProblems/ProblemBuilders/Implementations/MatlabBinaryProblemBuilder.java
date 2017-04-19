package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import CommunicationManager.IEvaluators.ISolutionEvaluation;
import ExternalProblems.MatlabImplementations.ExternalBinaryProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractBinaryProblemBuilder;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.BinarySolution;

public class MatlabBinaryProblemBuilder extends MatlabAbstractBinaryProblemBuilder<MatlabBinaryProblemBuilder>
{

    ISolutionEvaluation<BinarySolution> evaluator;

    public MatlabBinaryProblemBuilder(String problemName, ICommandManager manager, ISolutionEvaluation<BinarySolution> evaluator)
    {
        super(problemName, manager);
        this.evaluator = evaluator;
    }

    @Override
    public AbstractGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException
    {
        manager.setProblemPath(this.problemPath);
        manager.newObject(problemName, this.buildConstructorArguments());

        int numberOfBits = manager.getNumberOfBits();

        return new ExternalBinaryProblem(evaluator,
                this.getNumberOfVariables(), this.getNumberOfObjectives(),
                manager.getProblemName(), numberOfBits);
    }


}
