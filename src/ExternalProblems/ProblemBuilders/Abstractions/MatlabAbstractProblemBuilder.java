package ExternalProblems.ProblemBuilders.Abstractions;

import CommunicationManager.ICommandManager;
import ExternalProblems.ProblemBuilders.Implementations.FluentArrayBuilder;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

public abstract class MatlabAbstractProblemBuilder<T extends MatlabAbstractProblemBuilder<?>>
{
    protected String problemPath;
    protected String problemName;
    protected ICommandManager manager;

    protected int numberofVariables = 0;

    protected List<AbstractMatlabVariables> functionArguments = new ArrayList<AbstractMatlabVariables>();

    protected int getNumberOfVariables()
    {
        return manager.getNumberOfVariables();
    }

    protected int getNumberOfObjectives()
    {
        return manager.getNumberOfObjectives();
    }

    public MatlabAbstractProblemBuilder(String problemName, ICommandManager manager)
    {
        this.problemName = problemName;
        this.manager = manager;
    }

    public T setEvaluationFunctionName(String name)
    {
        this.manager.setEvaluateFunctionName(name);
        return (T)this;
    }

    public T setEvaluateConstraintsFunctionName(String name)
    {
        this.manager.setEvaluateConstraintsFunctionName(name);
        return (T)this;
    }

    public T setNumberOfVariablesDefaultFieldName(String numberOfVariablesDefaultField)
    {
        this.manager.setNumberOfVariablesFieldName(numberOfVariablesDefaultField);
        return (T)this;
    }

    public T setNumberOfObjectivesDefaultFieldeName(String numberOfObjectivesDefaultFielde)
    {
        this.manager.setNumberOfObjectivesFieldName(numberOfObjectivesDefaultFielde);
        return (T)this;
    }

    public T setNameDefaultFieldName(String nameDefaultField)
    {
        this.manager.setProblemNameFieldName(nameDefaultField);
        return (T)this;
    }

    public T setNameOfMatlabVariable(String name)
    {
        //this.nameOfCreatedVariable = name;
        manager.setObjectName(name);
        return (T)this;
    }

    public String buildConstructorArguments()
    {
        if(this.functionArguments.size()==0)
            return "";

        StringBuilder b = new StringBuilder();
        for(AbstractMatlabVariables item : this.functionArguments)
        {
            b.append(item.toMatlabStructure() + ",");
        }
        b.deleteCharAt(b.length()-1);
        return b.toString();
    }

    public T addConstructorArgument(AbstractMatlabVariables argument)
    {
        this.functionArguments.add(argument);
        return (T)this;
    }

    public T setProblemPath(String path)
    {
        this.problemPath = path;
        return (T)this;
    }

    public T addConstructorArgument(int argument)
    {
        this.functionArguments.add(new IntFunctionArgument(argument));
        return (T)this;
    }

    public T addConstructorArgument(double argument)
    {
        this.functionArguments.add(new DoubleFunctionArgument(argument));
        return (T)this;
    }

    public FluentArrayBuilder startArray()
    {
        return new FluentArrayBuilder(this);
    }

    public abstract AbstractGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException, JMException;

}
