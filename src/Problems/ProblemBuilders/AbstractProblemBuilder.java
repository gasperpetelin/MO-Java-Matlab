package Problems.ProblemBuilders;


import ConnectionManager.IManager;
import Problems.ProblemBuilders.MatlabVariables.AbstractVariable;
import Problems.ProblemBuilders.MatlabVariables.Implementations.IntMatlab;
import Problems.ProblemBuilders.MatlabVariables.Implementations.StrMatlab;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProblemBuilder<M extends IManager, T extends AbstractProblemBuilder<M, ?>>
{
    protected M manager;
    protected String problemName;
    protected List<AbstractVariable> arguments = new ArrayList<>();

    public T addValue(AbstractVariable variable)
    {
        arguments.add(variable);
        return (T)this;
    }

    public T addValue(Integer value)
    {
        arguments.add(new IntMatlab(value));
        return (T)this;
    }

    public T addValue(String value)
    {
        arguments.add(new StrMatlab(value));
        return (T)this;
    }

    protected String toMatlabCode()
    {
        if(this.arguments.size()==0)
            return "";
        StringBuilder b = new StringBuilder();
        for(AbstractVariable item : this.arguments)
        {
            b.append(item.toMatlabStructure() + ",");
        }
        b.deleteCharAt(b.length()-1);
        return b.toString();
    }

}
