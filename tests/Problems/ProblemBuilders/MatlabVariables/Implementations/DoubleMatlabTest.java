package Problems.ProblemBuilders.MatlabVariables.Implementations;

import Problems.ProblemBuilders.MatlabVariables.AbstractVariable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by gasper on 20. 04. 2017.
 */
class DoubleMatlabTest
{
    @Test
    void double_to_matlab_structure()
    {
        AbstractVariable v = new DoubleMatlab(3.4);
        assertEquals(3.4,v.getValue());
        assertEquals("3.4", v.toMatlabStructure());
    }

}