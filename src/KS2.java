import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;

import java.util.List;

public class KS2 extends AbstractBinaryProblem
{


    private List<Integer> elements;
    private Integer size;


    public KS2()
    {
        this.elements = elements;
        this.size = size;

        setNumberOfVariables(elements.size());
        setNumberOfObjectives(2);
        setName("Knapsack");

    }


    @Override
    protected int getBitsPerVariable(int i)
    {
        if(i<size)
            return 1;
        else
            throw new JMetalException("MAX VARIBLE");
    }

    @Override
    public void evaluate(BinarySolution s)
    {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < s.getNumberOfVariables(); i++)
        {
            BinarySet bs = s.getVariableValue(i);
            boolean b = bs.get(0);
            if(b)
            {
                sum +=this.elements.get(i);
                count++;
            }
        }

        if(sum>this.size)
        {
            sum=0;
            count=0;
        }




        s.setObjective(0, -sum);
        s.setObjective(1, count);
    }

}