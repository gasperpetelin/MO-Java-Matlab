package Problems.ProblemBuilders.MatlabVariables.Implementations;

import Problems.ProblemBuilders.MatlabVariables.AbstractVariable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntMatlabTest
{
    @Test
    public void integer_to_matlab_structure()
    {
        AbstractVariable v = new IntMatlab(3);
        assertEquals(3,v.getValue());
        assertEquals("3", v.toMatlabStructure());
    }
}