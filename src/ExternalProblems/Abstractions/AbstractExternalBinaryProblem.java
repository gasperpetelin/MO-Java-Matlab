package ExternalProblems.Abstractions;

import CommunicationManager.ICommandManager;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;

public abstract class AbstractExternalBinaryProblem extends AbstractExternalGenericProblem<BinarySolution> implements BinaryProblem
{


    protected int numberOfBits;

    public AbstractExternalBinaryProblem(ICommandManager manager, String nameOfObjectVariable)
    {
        super(manager, nameOfObjectVariable);
    }

    protected abstract int getBitsPerVariable(int var1);

    protected String MatlabMatrixBuilder(BinarySolution s)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < s.getNumberOfVariables(); i++)
        {
            builder.append("[" +s.getVariableValueString(i).replace("", " ").trim()+"];");
        }
        builder.append("]");
        return builder.toString();
    }

    public int getNumberOfBits(int index) {
        return this.getBitsPerVariable(index);
    }

    public int getTotalNumberOfBits() {
        int count = 0;

        for(int i = 0; i < this.getNumberOfVariables(); ++i) {
            count += this.getBitsPerVariable(i);
        }

        return count;
    }

    public BinarySolution createSolution() {
        return new DefaultBinarySolution(this);
    }
}
