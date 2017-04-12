package ExternalProblems.MatlabImplementations;

import CommunicationManager.MatlabCommandManager;
import ExternalProblems.ProblemBuilders.Implementations.MatlabBinaryProblemBuilder;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.ArrayFunctionArgument;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import matlabcontrol.MatlabConnectionException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatlabProblemBuilderTest
{
    @Test
    void build1() throws MatlabConnectionException
    {
        MatlabBinaryProblemBuilder b = new MatlabBinaryProblemBuilder("fake", MatlabCommandManager.newInstance());
        b.addConstructorArgument(3);
        b.addConstructorArgument(6.247);
        assertEquals("3,6.247", b.buildConstructorArguments());
    }

    @Test
    void build2() throws MatlabConnectionException
    {
        MatlabBinaryProblemBuilder b = new MatlabBinaryProblemBuilder("fake", MatlabCommandManager.newInstance());
        b.addConstructorArgument(3);
        b.addConstructorArgument(6.247);

        AbstractMatlabVariables a1 = new IntFunctionArgument(3);
        AbstractMatlabVariables a2 = new DoubleFunctionArgument(-1.725);
        List<AbstractMatlabVariables> l =  Arrays.asList(a1,a2);
        ArrayFunctionArgument a = new ArrayFunctionArgument(l);
        b.addConstructorArgument(a);

        assertEquals("3,6.247,[3,-1.725]", b.buildConstructorArguments());
    }

}