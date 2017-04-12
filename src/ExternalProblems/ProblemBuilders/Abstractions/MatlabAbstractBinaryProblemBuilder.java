package ExternalProblems.ProblemBuilders.Abstractions;

import CommunicationManager.ICommandManager;
import ExternalProblems.ProblemBuilders.Implementations.MatlabBinaryProblemBuilder;

public abstract class MatlabAbstractBinaryProblemBuilder<T extends MatlabAbstractBinaryProblemBuilder<?>>
        extends MatlabAbstractProblemBuilder<T>
{
    protected String bitsPerVariableDefaultField = "BitsPerVariable";

    public MatlabAbstractBinaryProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }
}
