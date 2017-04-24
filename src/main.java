import ConnectionManager.Implementations.BinaryMatlabManager;
import ConnectionManager.Implementations.DoubleMatlabManager;
import ConnectionManager.ManagerConfigs.Implementations.DoubleSolutionMatlabManagerConfig;
import Problems.ExternalBinaryProblem;
import Problems.ExternalDoubleProblem;
import Problems.ProblemBuilders.BinaryProblemBuilder;
import Problems.ProblemBuilders.DoubleProblemBuilder;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.*;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

public class main
{
    public static void main(String [] args)
    {
        BinaryMatlabManager manager = new BinaryMatlabManager();
        manager.openSession();
        manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
        BinaryProblemBuilder builder = new BinaryProblemBuilder(manager, "Knapsack")
                .startArray()
                    .addValue(5)
                    .addValue(3)
                    .addValue(7)
                    .addValue(2)
                    .addValue(3)
                    .addValue(3)
                .stopArray()
                .addValue(11)
                .setNumberOfObjectives(2);
        ExternalBinaryProblem p = builder.build();
        Algorithm<BinarySolution> algorithm1 = new NSGAII(p, 600, 20,
                new SinglePointCrossover(0.5),
                new BitFlipMutation(0.5),
                new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
        AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1).execute();
        System.out.println(algorithm1.getResult());
        System.out.println(p.getName());
        manager.closeSession();
        T1();
    }

    public static void T1()
    {
        DoubleSolutionMatlabManagerConfig conf = new DoubleSolutionMatlabManagerConfig();
        DoubleMatlabManager manager = new DoubleMatlabManager(conf);
        manager.openSession();

        manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
        DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ZagonTest")
                .setNumberOfVariables(4)
                .setNumberOfObjectives(10)
                .setProblemName("testName")
                .addLimit(-100, 100)
                .addLimit(-100, 100)
                .addLimit(-100, 100)
                .addLimit(-100, 100);

        ExternalDoubleProblem p = builder.build();

        Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(p,
                new SBXCrossover(0.9, 20),
                new PolynomialMutation(0.2, 20))
                .setPopulationSize(60)
                .setMaxEvaluations(2500)
                .build();
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        System.out.println(algorithm.getResult());
        System.out.println(algorithmRunner.getComputingTime());
        System.out.println(p.getName());

        manager.closeSession();
    }


}