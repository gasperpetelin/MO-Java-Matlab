package ExternalProblems.ProblemBuilders.Abstractions;

import CommunicationManager.ICommandManager;
import ExternalProblems.Abstractions.AbstractExternalGenericProblem;
import ExternalProblems.ProblemBuilders.Implementations.FluentArrayBuilder;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

public abstract class MatlabAbstractProblemBuilder<T extends MatlabAbstractProblemBuilder<?>>
{
    protected String problemPath;
    protected String problemName;
    protected ICommandManager manager;

    protected String numberOfVariablesDefaultField = "NumberOfVariables";
    protected String numberOfObjectivesDefaultField = "NumberOfObjectives";
    protected String nameDefaultField = "Name";
    protected String nameOfCreatedVariable = "createdVariableName";

    protected List<AbstractMatlabVariables> functionArguments = new ArrayList<AbstractMatlabVariables>();

    protected int getNumberOfVariables() throws MatlabConnectionException, InstantiationException, IllegalAccessException, MatlabInvocationException
    {
        return manager.getVariable(nameOfCreatedVariable + "." + numberOfVariablesDefaultField, IntFunctionArgument.class).getValue();
    }

    protected int getNumberOfObjectives() throws MatlabConnectionException, InstantiationException, IllegalAccessException, MatlabInvocationException
    {
        return manager.getVariable(nameOfCreatedVariable + "." + numberOfObjectivesDefaultField, IntFunctionArgument.class).getValue();
    }

    public MatlabAbstractProblemBuilder(String problemName, ICommandManager manager)
    {
        this.problemName = problemName;
        this.manager = manager;
    }

    public T setNumberOfVariablesDefaultFieldName(String numberOfVariablesDefaultField)
    {
        this.numberOfVariablesDefaultField = numberOfVariablesDefaultField;
        return (T)this;
    }

    public T setNumberOfObjectivesDefaultFieldeName(String numberOfObjectivesDefaultFielde)
    {
        this.numberOfObjectivesDefaultField = numberOfObjectivesDefaultFielde;
        return (T)this;
    }

    public T setNameDefaultFieldName(String nameDefaultField)
    {
        this.nameDefaultField = nameDefaultField;
        return (T)this;
    }

    public T setNameOfMAtlabVariable(String name)
    {
        this.nameOfCreatedVariable = name;
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

    public abstract AbstractExternalGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException, JMException;

}
