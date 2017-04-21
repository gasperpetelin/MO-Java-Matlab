package Problems.ProblemBuilders.MatlabVariables;


public abstract class AbstractVariable<T>
{
    protected T value;
    public AbstractVariable(T value)
    {
        this.value = value;
    }
    public T getValue()
    {
        return this.value;
    }

    public String toMatlabStructure()
    {
        return this.value.toString();
    }
}
