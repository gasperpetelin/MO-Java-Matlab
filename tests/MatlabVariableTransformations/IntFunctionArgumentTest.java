package MatlabVariableTransformations;

import MatlabVariableTransformations.Implementations.IntFunctionArgument;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntFunctionArgumentTest
{
    @org.junit.jupiter.api.Test
    void toMatlabStructure()
    {
        AbstractMatlabVariables a = new IntFunctionArgument(3);
        assertEquals("3", a.toMatlabStructure());
    }

}