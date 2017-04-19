package ExternalProblems.Abstractions;

import MatlabVariableTransformations.Implementations.StringFunctionArgument;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;

public abstract class AbstractExternalBinaryProblem extends org.uma.jmetal.problem.impl.AbstractGenericProblem<BinarySolution> implements BinaryProblem
{
    public AbstractExternalBinaryProblem(int numberOfBits, String problemName)
    {
        this.numberOfBits = numberOfBits;
        this.setName(problemName);
    }

    protected int numberOfBits;

    protected abstract int getBitsPerVariable(int var1);

    public int getNumberOfBits(int index)
    {
        return this.getBitsPerVariable(index);
    }

    public int getTotalNumberOfBits() {
        int count = 0;

        for(int i = 0; i < this.getNumberOfVariables(); ++i) {
            count += this.getBitsPerVariable(i);
        }

        return count;
    }

    public BinarySolution createSolution()
    {
        return new DefaultBinarySolution(this);
    }
}
