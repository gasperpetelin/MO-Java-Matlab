import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;

public class Test
{
    public static void main(String[] args)
    {
        BinaryProblem b = new OneMax();

        Algorithm<BinarySolution> algorithm;

        SinglePointCrossover co = new SinglePointCrossover(0.3);
        BitFlipMutation bf = new BitFlipMutation(0.5);

        algorithm = new GeneticAlgorithmBuilder(b, co, bf).build();



        AlgorithmRunner algRun = new AlgorithmRunner.Executor(algorithm).execute();

        System.out.println(algorithm.getResult());

    }
}
