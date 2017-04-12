package MatlabVariableTransformations;

import MatlabVariableTransformations.Implementations.ArrayFunctionArgument;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by gasper on 8. 04. 2017.
 */
class ArrayFunctionArgumentTest
{
    @Test
    void toMatlabStructure1()
    {
        AbstractMatlabVariables a1 = new IntFunctionArgument(3);
        AbstractMatlabVariables a2 = new IntFunctionArgument(5);
        AbstractMatlabVariables a3 = new IntFunctionArgument(-1);
        AbstractMatlabVariables a4 = new IntFunctionArgument(0);
        List<AbstractMatlabVariables> l =  Arrays.asList(a1,a2,a3,a4);
        ArrayFunctionArgument a = new ArrayFunctionArgument(l);
        assertEquals("[3,5,-1,0]",a.toMatlabStructure());
    }

    @Test
    void toMatlabStructure2()
    {
        AbstractMatlabVariables a1 = new IntFunctionArgument(3);
        AbstractMatlabVariables a2 = new IntFunctionArgument(5);
        AbstractMatlabVariables a3 = new DoubleFunctionArgument(-1.725);
        AbstractMatlabVariables a4 = new DoubleFunctionArgument(0.0);
        List<AbstractMatlabVariables> l =  Arrays.asList(a1,a2,a3,a4);
        ArrayFunctionArgument a = new ArrayFunctionArgument(l);
        assertEquals("[3,5,-1.725,0.0]",a.toMatlabStructure());
    }

}