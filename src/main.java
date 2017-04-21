import ConnectionManager.Implementations.BinaryMatlabManager;
import ConnectionManager.Implementations.DoubleMatlabManager;
import ConnectionManager.ManagerConfigs.Implementations.BinarySolutionMatlabManagerConfig;
import ConnectionManager.ManagerConfigs.Implementations.DoubleSolutionMatlabManagerConfig;
import Problems.ExternalBinaryProblem;
import Problems.ExternalDoubleProblem;
import Problems.ProblemBuilders.BinaryProblemBuilder;
import Problems.ProblemBuilders.DoubleProblemBuilder;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class main
{
    public static void main(String [] args)
    {
        //BinarySolutionMatlabManagerConfig conf = new BinarySolutionMatlabManagerConfig();
        //BinaryMatlabManager manager = new BinaryMatlabManager(conf);
        //manager.openSession();
        //manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
        //BinaryProblemBuilder builder = new BinaryProblemBuilder(manager, "Knapsack")
        //        .startArray()
        //            .addValue(5)
        //            .addValue(3)
        //            .addValue(7)
        //            .addValue(2)
        //            .addValue(3)
        //            .addValue(3)
        //        .stopArray()
        //        .addValue(11);
        //ExternalBinaryProblem p = builder.build();
        //Algorithm<BinarySolution> algorithm1 = new NSGAII(p, 600, 20,
        //        new SinglePointCrossover(0.5),
        //        new BitFlipMutation(0.5),
        //        new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
        //AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1).execute();
        //System.out.println(algorithm1.getResult());
        //System.out.println(p.getName());
        //manager.closeSession();
        T1();
    }

    public static void T1()
    {
        DoubleSolutionMatlabManagerConfig conf = new DoubleSolutionMatlabManagerConfig();
        DoubleMatlabManager manager = new DoubleMatlabManager(conf);

        manager.openSession();
        manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
        DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ZagonTest");
        ExternalDoubleProblem p = builder.build();
        Algorithm<DoubleSolution> algorithm1 = new NSGAII(p, 600, 20,
                new SBXCrossover(0.9, 20),
                new PolynomialMutation(2, 20),
                new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
        AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1)
                .execute();
        System.out.println(algorithm1.getResult());
        System.out.println(p.getName());
        manager.closeSession();
    }


}