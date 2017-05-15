package InputParser;

import Problems.ExternalDoubleProblem;
import Problems.PopulationLogger.AlgorithmMetaData;
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
    public static AlgorithmMetadataPair getAlgorithm(String type,
                                                               ExternalDoubleProblem problem,
                                                               CrossoverOperator<DoubleSolution> cross,
                                                               MutationOperator<DoubleSolution>mut,
                                                               int maxeval,
                                                               int popSize) throws ParseException
    {

        Algorithm<List<DoubleSolution>> algo = null;

        if(type != null)
        {
            switch (type)
            {
                case "nsgaii":
                    algo = new NSGAIIBuilder<>(problem, cross, mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "ibea":
                    algo = new IBEABuilder(problem)
                            .setArchiveSize(5)
                            .setCrossover(cross)
                            .setMutation(mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "random":
                    algo = new RandomSearchBuilder(problem)
                            .setMaxEvaluations(maxeval)
                            .build();
                    popSize = 1;
                    break;
                case "gde3":
                    algo = new GDE3Builder(problem)
                            .setPopulationSize(popSize)
                            .setMaxEvaluations(maxeval)
                            .build();
                    break;
                case "spea2":
                    algo = new SPEA2Builder(problem, cross, mut)
                            .setMaxIterations((int)Math.floor(maxeval/popSize))
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "paes":
                    algo = new PAESBuilder(problem)
                            .setMutationOperator(mut)
                            .setMaxEvaluations(maxeval)
                            .setArchiveSize(popSize)
                            .build();
                    break;
                default:
                    throw new ParseException("No algorithm with name: " + type);
            }
        }
        if(algo==null)
            return null;

        AlgorithmMetaData meta = new AlgorithmMetaData(algo, mut, cross, maxeval, popSize);
        AlgorithmMetadataPair pair = new AlgorithmMetadataPair(algo, meta);

        return pair;
    }
}
