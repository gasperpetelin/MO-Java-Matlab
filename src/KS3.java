import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.List;

public class KS3 extends AbstractBinaryProblem //implements ConstrainedProblem<BinarySolution>
{
    private List<ElementPairs> elements;
    private double size;


    public KS3(List<ElementPairs> elements, double size)
    {
        this.elements = elements;
        this.size = size;

        setNumberOfVariables(elements.size());
        setNumberOfObjectives(2);
        setNumberOfConstraints(1);
        setName("Knapsack");

        //overallConstraintViolationDegree = new OverallConstraintViolation<BinarySolution>() ;
        //numberOfViolatedConstraints = new NumberOfViolatedConstraints<BinarySolution>() ;

    }


    @Override
    protected int getBitsPerVariable(int i)
    {
        if(i<size)
            return 1;
        else
            throw new JMetalException("MAX VARIBLE") ;
    }

    @Override
    public void evaluate(BinarySolution s)
    {
        double sumPrice = 0;
        double sumWeights = 0;

        for (int i = 0; i < s.getNumberOfVariables(); i++)
        {
            BinarySet bs = s.getVariableValue(i);
            boolean b = bs.get(0);
            System.out.println("" + bs.get(0));
            if(b)
            {
                sumPrice += this.elements.get(i).getValue();
                sumWeights += this.elements.get(i).getWeight();
            }
        }

        if(sumWeights>this.size)
        {
            s.setObjective(0, 1);
            s.setObjective(1, 1);
        }
        else
        {
            s.setObjective(0, -sumPrice);
            s.setObjective(1, sumWeights);
        }
    }

    //public OverallConstraintViolation<BinarySolution> overallConstraintViolationDegree ;
    //public NumberOfViolatedConstraints<BinarySolution> numberOfViolatedConstraints ;

    //@Override
    //public void evaluateConstraints(BinarySolution binarySolution)
    //{
    //    BinarySet bs = binarySolution.getVariableValue(3);
    //    boolean b = bs.get(0);
    //    if(b)
    //    {
    //        overallConstraintViolationDegree.setAttribute(binarySolution, -20.0);
    //        numberOfViolatedConstraints.setAttribute(binarySolution, 1);
    //    }
    //    else
    //    {
    //        overallConstraintViolationDegree.setAttribute(binarySolution, 0.0);
    //        numberOfViolatedConstraints.setAttribute(binarySolution, 0);
    //    }
    //}
}
