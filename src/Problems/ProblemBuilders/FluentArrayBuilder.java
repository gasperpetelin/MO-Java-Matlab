package Problems.ProblemBuilders;


import Problems.ProblemBuilders.MatlabVariables.Implementations.ArrayMatlab;
import Problems.ProblemBuilders.MatlabVariables.Implementations.DoubleMatlab;
import Problems.ProblemBuilders.MatlabVariables.Implementations.IntMatlab;
import Problems.ProblemBuilders.MatlabVariables.Implementations.StrMatlab;

public class FluentArrayBuilder<T extends AbstractProblemBuilder<?, ?>>
{
    private T builder;
    protected ArrayMatlab array = new ArrayMatlab();
    public FluentArrayBuilder(T problemBuilder)
    {
        this.builder = problemBuilder;
    }

    public T stopArray()
    {
        this.builder.addValue(this.array);
        return (T)builder;
    }

    public FluentArrayBuilder<T> addValue(Integer value)
    {
        this.array.addElement(new IntMatlab(value));
        return this;
    }

    public FluentArrayBuilder<T> addValue(Double value)
    {
        this.array.addElement(new DoubleMatlab(value));
        return this;
    }

    public FluentArrayBuilder<T> addValue(String value)
    {
        this.array.addElement(new StrMatlab(value));
        return this;
    }


}
