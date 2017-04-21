package Problems.ProblemBuilders;


import ConnectionManager.Implementations.BinaryMatlabManager;
import Problems.ExternalBinaryProblem;

public class BinaryProblemBuilder extends AbstractProblemBuilder<BinaryMatlabManager, BinaryProblemBuilder>
{
    public ExternalBinaryProblem build()
    {
        this.manager.newObject(this.problemName, this.toMatlabCode());

        int numberOfVariables = manager.getNumberOfVriables();
        int numberOfObjectives = manager.getNumberOfObjectives();
        int numberOfBitsPerVariable = manager.getBitsPerVariable();

        return new ExternalBinaryProblem(manager, numberOfVariables, numberOfObjectives,  numberOfBitsPerVariable);
    }

    public BinaryProblemBuilder(BinaryMatlabManager manager, String problemName)
    {
        this.manager = manager;
        this.problemName = problemName;
    }

    public FluentArrayBuilder<BinaryProblemBuilder> startArray()
    {
        return new FluentArrayBuilder<>(this);
    }


}
