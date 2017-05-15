package InputParser;

import org.apache.commons.cli.ParseException;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.solution.DoubleSolution;

public class CrossoverFactory
{
    public static CrossoverOperator<DoubleSolution> getCrossover(String[] parameters) throws ParseException
    {
        if((parameters != null) && (parameters.length != 0))
        {
            String crossoverType = parameters[0].toLowerCase();
            switch (crossoverType)
            {
                case "blxalpha":
                    if (parameters.length == 3)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        double alpha = Double.parseDouble(parameters[2]);
                        return new BLXAlphaCrossover(crossoverProbability, alpha);
                    }
                    if (parameters.length == 2)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        return new BLXAlphaCrossover(crossoverProbability);
                    }
                    break;

                case "null":
                    return new NullCrossover<>();

                case "sbx":
                    if (parameters.length == 3)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        double distributionIndex = Double.parseDouble(parameters[2]);
                        return new SBXCrossover(crossoverProbability, distributionIndex);
                    }
                    break;
                default:
                    throw new ParseException("No crossover operation with name: " + crossoverType);
            }
        }
        return null;
    }
}
