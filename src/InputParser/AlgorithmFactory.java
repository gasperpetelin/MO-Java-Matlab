package InputParser;

import Problems.ExternalDoubleProblem;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class AlgorithmFactory
{
    public static Algorithm<List<DoubleSolution>> getAlgorithm(String type,
                                                               ExternalDoubleProblem problem,
                                                               CrossoverOperator<DoubleSolution> cross,
                                                               MutationOperator<DoubleSolution>mut,
                                                               int maxeval,
                                                               int popSize) throws ParseException
    {


        if(type != null)
        {
            switch (type)
            {
                case "nsgaii":
                    return new NSGAIIBuilder<>(problem, cross, mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                case "ibea":
                    return new IBEABuilder(problem)
                            .setArchiveSize(5)
                            .setCrossover(cross)
                            .setMutation(mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                case "random":
                    return new RandomSearchBuilder(problem)
                            .setMaxEvaluations(maxeval)
                            .build();
                case "gde3":
                    return new GDE3Builder(problem)
                            .setPopulationSize(popSize)
                            .setMaxEvaluations(maxeval)
                            .build();
                case "spea2":
                    return new SPEA2Builder(problem, cross, mut)
                            .setMaxIterations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                case "paes":
                    return new PAESBuilder(problem)
                            .setMutationOperator(mut)
                            .setMaxEvaluations(maxeval)
                            .build();
                default:
                    throw new ParseException("No algorithm with name: " + type);
            }
        }
        return null;
    }
}
