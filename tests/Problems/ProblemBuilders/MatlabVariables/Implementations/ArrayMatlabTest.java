package Problems.ProblemBuilders.MatlabVariables.Implementations;

import Problems.ProblemBuilders.MatlabVariables.AbstractVariable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ArrayMatlabTest
{
    @Test
    void to_matlab_structure()
    {
        AbstractVariable v1 = new DoubleMatlab(3.4);
        AbstractVariable v2 = new IntMatlab(3);

        ArrayMatlab ar = new ArrayMatlab();
        ar.addElement(v1);
        ar.addElement(v2);

        assertEquals("[3.4,3]", ar.toMatlabStructure());
    }

    @Test
    void to_matlab_structure2()
    {
        AbstractVariable v1 = new DoubleMatlab(3.4);
        AbstractVariable v2 = new IntMatlab(3);
        List<AbstractVariable> l = new ArrayList<>();
        l.add(v1);
        ArrayMatlab ar = new ArrayMatlab(l);
        ar.addElement(v1);
        ar.addElement(v2);

        assertEquals("[3.4,3.4,3]", ar.toMatlabStructure());
    }

}