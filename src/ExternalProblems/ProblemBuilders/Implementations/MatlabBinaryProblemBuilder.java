package ExternalProblems.ProblemBuilders.Implementations;


import CommunicationManager.ICommandManager;
import ExternalProblems.Abstractions.AbstractExternalGenericProblem;
import ExternalProblems.MatlabImplementations.ExternalBinaryProblem;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractBinaryProblemBuilder;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractProblemBuilder;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public class MatlabBinaryProblemBuilder extends MatlabAbstractBinaryProblemBuilder<MatlabBinaryProblemBuilder>
{


    public MatlabBinaryProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }

    @Override
    public AbstractExternalGenericProblem build() throws MatlabConnectionException, MatlabInvocationException, InstantiationException, IllegalAccessException
    {
        manager.openSession();
        manager.setProblemPath(this.problemPath);
        manager.newObject(nameOfCreatedVariable, problemName, this.buildConstructorArguments());


        int numberOfVariables = this.getNumberOfVariables();
        int numberofObjectives = this.getNumberOfObjectives();
        //String problemName = manager.getVariable(nameOfCreatedVariable + "." + nameDefaultField, StringFunctionArgument.class).getValue();


        return new ExternalBinaryProblem(manager, nameOfCreatedVariable,
                numberOfVariables, numberofObjectives,
                nameDefaultField, bitsPerVariableDefaultField);
    }


}
