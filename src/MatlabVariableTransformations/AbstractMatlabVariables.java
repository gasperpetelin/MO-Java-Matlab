package MatlabVariableTransformations;

import org.uma.jmetal.util.JMetalException;

public abstract class AbstractMatlabVariables<T>
{
    protected T value;

    public AbstractMatlabVariables()
    {
        this.value=null;
    }
    public void setValue(T value)
    {
        this.value = value;
    }
    public abstract void setMatlabValue(Object value);
    public T getValue()
    {
        return this.value;
    }
    public String toMatlabStructure()
    {
        if(this.value==null)
            throw new JMetalException("Argument is null");
        return this.value.toString();
    }
}
