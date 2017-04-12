import CommunicationManager.ICommandManager;
import CommunicationManager.MatlabCommandManager;
import ExternalProblems.Abstractions.AbstractExternalGenericProblem;
import ExternalProblems.ProblemBuilders.Implementations.MatlabBinaryProblemBuilder;
import ExternalProblems.ProblemBuilders.Implementations.MatlabConstrainedDoubleProblemBuilder;
import ExternalProblems.ProblemBuilders.Implementations.MatlabDoubleProblemBuilder;
import ExternalProblems.ProblemBuilders.Abstractions.MatlabAbstractProblemBuilder;
import matlabcontrol.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;



public class jMetalTest {
    public static void main(String [ ] args) throws MatlabInvocationException, MatlabConnectionException, InstantiationException, IllegalAccessException, JMException
    {
        ICommandManager manager1 = MatlabCommandManager.newInstance();
        //MatlabAbstractProblemBuilder builder1 = new MatlabDoubleProblemBuilder("Binh", manager1)
        //        .setProblemPath("C:\\Users\\gasper\\Desktop\\jMetalTest\\matlabscripts");
        //        //.setLimit(-7, 4)
        //        //.setLimit(-7, 4);
        //AbstractExternalGenericProblem p1 = builder1.build();

        MatlabConstrainedDoubleProblemBuilder builder1 = new MatlabConstrainedDoubleProblemBuilder("Binh", manager1)
                .setProblemPath("C:\\Users\\gasper\\Desktop\\jMetalTest\\matlabscripts");
        AbstractExternalGenericProblem p1 = builder1.build();

        Algorithm<DoubleSolution> algorithm1 = new NSGAII(p1, 60, 2,
                new SBXCrossover(0.9, 20),
                new PolynomialMutation(2, 20),
                new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
        AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1)
                .execute();
        System.out.println(algorithm1.getResult());
        p1.closeSession();



        //ICommandManager manager = MatlabCommandManager.newInstance();
        //MatlabAbstractProblemBuilder builder = new MatlabBinaryProblemBuilder("Knapsack", manager)
        //        .setProblemPath("C:\\Users\\gasper\\Desktop\\jMetalTest\\matlabscripts")
        //        .startArray()
        //            .addConstructorArgument(5)
        //            .addConstructorArgument(3)
        //            .addConstructorArgument(7)
        //            .addConstructorArgument(2)
        //            .addConstructorArgument(3)
        //            .addConstructorArgument(3)
        //        .stopArray()
        //        .addConstructorArgument(11);
        //AbstractExternalGenericProblem p = builder.build();
        //Algorithm<BinarySolution> algorithm = new NSGAII(p, 600, 20,
        //        new SinglePointCrossover(0.3),
        //        new BitFlipMutation(0.5),
        //        new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
        //AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        //        .execute();
        //System.out.println(algorithm.getResult());
        //System.out.println(algorithmRunner.getComputingTime());
        //p.closeSession();


    }

    public static void T2() throws MatlabConnectionException, MatlabInvocationException
    {
        MatlabProxyFactoryOptions.Builder builder = new MatlabProxyFactoryOptions.Builder();
// setup the factory
//    setCopyPasteCallback() connects to an existing MATLAB by copy-pasting a few lines into the command window
        builder.setUsePreviouslyControlledSession(true);// starts a new MATLAB or connects to a previously started MATLAB without any user intervention

        MatlabProxyFactory factory = new MatlabProxyFactory(builder.build());
// get the proxy
        MatlabProxy proxy = factory.getProxy();
// do stuff over the proxy
        proxy.eval("disp('hello world!')");
        proxy.setVariable("a", 5.0);
        Object a = proxy.getVariable("a");
        double actual = ((double[]) a)[0];
        proxy.disconnect();
    }

    public static void T1() throws MatlabConnectionException, IllegalAccessException, MatlabInvocationException, InstantiationException
    {
        //List<Integer> l = new ArrayList<>();
        //l.add(3);
        //l.add(5);
        //l.add(3);
        //l.add(2);
        //l.add(8); //size8

        List<ElementPairs> l = new ArrayList<>();
        l.add(new ElementPairs(4,8));
        l.add(new ElementPairs(5,10));
        l.add(new ElementPairs(8,15));
        l.add(new ElementPairs(3,4));

        BinaryProblem p = new KS3(l, 11);

        Algorithm<BinarySolution> algorithm;

        SinglePointCrossover crossover = new SinglePointCrossover(0.3);
        BitFlipMutation mutation = new BitFlipMutation(0.5);

        algorithm = new GeneticAlgorithmBuilder(p, crossover, mutation).setMaxEvaluations(5000).setPopulationSize(500).build();
        algorithm = new NSGAIIBuilder(p, crossover, mutation).setMaxEvaluations(50000).setPopulationSize(100).build();

        algorithm = new NSGAII(p, 30, 2, crossover, mutation,
               new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());


        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        System.out.println(algorithm.getResult());
        System.out.println(algorithmRunner.getComputingTime());
    }

}
