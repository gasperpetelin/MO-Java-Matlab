package Problems.ProblemBuilders.MatlabVariables.Implementations;


import Problems.ProblemBuilders.MatlabVariables.AbstractVariable;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;

public class ArrayMatlab extends AbstractVariable<List<AbstractVariable>>
{
    public ArrayMatlab(List<AbstractVariable> value)
    {
        super(value);
    }

    public ArrayMatlab()
    {
        super(new ArrayList<>());
    }

    @Override
    public String toMatlabStructure()
    {
        if(this.value==null)
            throw new JMetalException("argument is null");
        StringBuilder b = new StringBuilder();
        b.append("[");
        for(AbstractVariable item : this.value)
        {
            b.append(item.toMatlabStructure() + ",");
        }
        b.deleteCharAt(b.length()-1);
        b.append("]");
        return b.toString();
    }

    public void addElement(AbstractVariable element)
    {
        this.value.add(element);
    }


}
