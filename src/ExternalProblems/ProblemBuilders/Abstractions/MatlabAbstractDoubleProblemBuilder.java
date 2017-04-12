package ExternalProblems.ProblemBuilders.Abstractions;


import CommunicationManager.ICommandManager;

public abstract class MatlabAbstractDoubleProblemBuilder<T extends MatlabAbstractDoubleProblemBuilder<?>>
        extends MatlabAbstractProblemBuilder<T>
{
    public MatlabAbstractDoubleProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }
}
