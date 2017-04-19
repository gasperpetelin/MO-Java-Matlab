package MatlabVariableTransformations.Implementations;


import MatlabVariableTransformations.AbstractMatlabVariables;

public class IntFunctionArgument extends AbstractMatlabVariables<Integer>
{
    public IntFunctionArgument(Integer number)
    {
        this.value = number;
    }
    public IntFunctionArgument()
    {
        this.value = 0;
    }

    @Override
    public void setMatlabValue(Object value)
    {
        Double d = ((double[]) value)[0];
        this.value = d.intValue();
    }

}
