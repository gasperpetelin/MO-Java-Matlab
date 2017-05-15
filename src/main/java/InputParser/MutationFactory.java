package InputParser;


import org.apache.commons.cli.ParseException;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.*;
import org.uma.jmetal.solution.DoubleSolution;

public class MutationFactory
{
    public static MutationOperator<DoubleSolution> getMutation(String[] parameters) throws ParseException
    {

        if((parameters != null) && (parameters.length != 0))
        {
            String mutationType = parameters[0].toLowerCase();
            switch (mutationType)
            {
                case "null":
                    return new NullMutation<>();
                case "nonuniform":
                    if(parameters.length == 4)
                    {
                        double mutationProbability = Double.parseDouble(parameters[1]);
                        double perturbation = Double.parseDouble(parameters[2]);
                        int maxIterations = Integer.parseInt(parameters[3]);
                        return new NonUniformMutation(mutationProbability, perturbation, maxIterations);
                    }
                    break;
                case "polynomial":
                    if(parameters.length == 3)
                    {
                        double mutationProbability = Double.parseDouble(parameters[1]);
                        double distributionIndex = Double.parseDouble(parameters[2]);
                        return new PolynomialMutation(mutationProbability, distributionIndex);
                    }
                    break;
                case "simplerandom":
                    if(parameters.length == 2)
                    {
                        double mutationProbability = Double.parseDouble(parameters[1]);
                        return new SimpleRandomMutation(mutationProbability);
                    }
                    break;
                case "uniform":
                    if(parameters.length == 3)
                    {
                        double mutationProbability = Double.parseDouble(parameters[1]);
                        double perturbation = Double.parseDouble(parameters[2]);
                        return new UniformMutation(mutationProbability, perturbation);
                    }
                    break;
                default:
                    throw new ParseException("No mutation operation with name: " + mutationType);
            }
        }
        return null;
    }
}
