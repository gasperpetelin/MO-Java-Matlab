package Problems.ProblemBuilders;


import ConnectionManager.CommandExecutionException;
import ConnectionManager.Implementations.BinaryMatlabManager;
import Problems.ExternalBinaryProblem;

public class BinaryProblemBuilder extends AbstractProblemBuilder<BinaryMatlabManager, BinaryProblemBuilder>
{
    int numberOfBitsPerVariable = -1;

    public ExternalBinaryProblem build() throws CommandExecutionException
    {
        this.manager.newObject(this.problemName, this.toMatlabCode());

        if(this.overriddenNumberOfVariables<1)
            this.overriddenNumberOfVariables = manager.getNumberOfVariables();

        if(this.overriddenNumberOfObjectives<1)
            this.overriddenNumberOfObjectives = manager.getNumberOfObjectives();

        if(this.numberOfBitsPerVariable<1)
            this.numberOfBitsPerVariable = manager.getBitsPerVariable();

        String problemName = manager.getProblemName();

        return new ExternalBinaryProblem(manager, problemName, this.overriddenNumberOfVariables,
                this.overriddenNumberOfObjectives,  this.numberOfBitsPerVariable);
    }

    public BinaryProblemBuilder(BinaryMatlabManager manager, String problemName)
    {
        this.manager = manager;
        this.problemName = problemName;
    }

    public BinaryProblemBuilder setNumberOfBitsPerVariable(int bits)
    {
        this.numberOfBitsPerVariable = bits;
        return this;
    }

    public FluentArrayBuilder<BinaryProblemBuilder> startArray()
    {
        return new FluentArrayBuilder<>(this);
    }


}
