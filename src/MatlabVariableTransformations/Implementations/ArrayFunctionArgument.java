package MatlabVariableTransformations.Implementations;


import MatlabVariableTransformations.AbstractMatlabVariables;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;

public class ArrayFunctionArgument extends AbstractMatlabVariables<List<AbstractMatlabVariables>>
{
    public ArrayFunctionArgument(List<AbstractMatlabVariables> list)
    {
        this.value = list;
    }
    public ArrayFunctionArgument()
    {
        this.value = new ArrayList<>();
    }

    @Override
    public void setMatlabValue(Object value)
    {
        this.value = new ArrayList<>();
        double[] array = (double[])value;
        for(int i =0; i<array.length; i++)
        {
            this.value.add(new DoubleFunctionArgument(array[i]));
        }
    }

    @Override
    public String toMatlabStructure()
    {
        if(this.value==null)
            throw new JMetalException("argument is null");
        StringBuilder b = new StringBuilder();
        b.append("[");
        for(AbstractMatlabVariables item : this.value)
        {
            b.append(item.toMatlabStructure() + ",");
        }
        b.deleteCharAt(b.length()-1);
        b.append("]");
        return b.toString();
    }


    @Override
    public List<AbstractMatlabVariables> getValue()
    {
        return this.value;
    }


}
