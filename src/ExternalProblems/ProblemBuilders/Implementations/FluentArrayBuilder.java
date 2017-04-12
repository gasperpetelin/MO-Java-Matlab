package ExternalProblems.ProblemBuilders.Implementations;


import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractProblemBuilder;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.ArrayFunctionArgument;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;

import java.util.ArrayList;
import java.util.List;

public class FluentArrayBuilder
{
    protected List<AbstractMatlabVariables> functionArguments = new ArrayList<AbstractMatlabVariables>();
    MatlabAbstractProblemBuilder builder;


    public FluentArrayBuilder(MatlabAbstractProblemBuilder builder)
    {
        this.builder = builder;
    }

    public FluentArrayBuilder addConstructorArgument(int argument)
    {
        this.functionArguments.add(new IntFunctionArgument(argument));
        return this;
    }

    public FluentArrayBuilder addConstructorArgument(double argument)
    {
        this.functionArguments.add(new DoubleFunctionArgument(argument));
        return this;
    }

    public MatlabAbstractProblemBuilder stopArray()
    {
        this.builder.addConstructorArgument(new ArrayFunctionArgument(this.functionArguments));
        return this.builder;
    }
}
