package ExternalProblems.ProblemBuilders.Abstractions;

import CommunicationManager.ICommandManager;

public abstract class MatlabAbstractBinaryProblemBuilder<T extends MatlabAbstractBinaryProblemBuilder<?>>
        extends MatlabAbstractProblemBuilder<T>
{
    public void setBitsPerVariableDefaultField(String bitsPerVariableDefaultField)
    {
        this.manager.setBitsPerVariableFieldName(bitsPerVariableDefaultField);
    }

    public MatlabAbstractBinaryProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }
}
