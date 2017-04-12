package MatlabVariableTransformations.Implementations;

import MatlabVariableTransformations.AbstractMatlabVariables;


public class DoubleFunctionArgument extends AbstractMatlabVariables<Double>
{
    public DoubleFunctionArgument(Double number)
    {
        this.value = number;
    }

    public DoubleFunctionArgument()
    {
        this.value = 0.0;
    }

    @Override
    public void setMatlabValue(Object value)
    {
        this.value = ((double[]) value)[0];
    }

    @Override
    public Double getValue()
    {
        return this.value;
    }
}
