package MatlabVariableTransformations;

import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DoubleFunctionArgumentTest
{
    @Test
    void toMatlabStructure()
    {
        AbstractMatlabVariables a = new DoubleFunctionArgument(3.58276);
        assertEquals("3.58276", a.toMatlabStructure());
    }

}