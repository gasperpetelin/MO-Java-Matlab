package MatlabVariableTransformations.Implementations;

import MatlabVariableTransformations.AbstractMatlabVariables;

public class StringFunctionArgument extends AbstractMatlabVariables<String>
{
    public StringFunctionArgument(String number)
    {
        this.value = number;
    }
    public StringFunctionArgument()
    {
        this.value = "";
    }

    @Override
    public void setMatlabValue(Object value)
    {
        this.value = value.toString();
    }

}
