import ConnectionManager.Implementations.DoubleMatlabManager;
import Problems.ExternalDoubleProblem;
import Problems.PopulationLogger.FileDoubleLogger;
import Problems.PopulationLogger.IPopulationLogger;
import Problems.PopulationLogger.NullDoubleLogger;
import Problems.ProblemBuilders.DoubleProblemBuilder;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;

import java.util.List;

public class main
{
    public static void main(String [] args) throws Exception
    {




        //DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        //line_chart_dataset.addValue( 15 , "schools" , "1970" );
        //line_chart_dataset.addValue( 30 , "schools" , "1980" );
        //line_chart_dataset.addValue( 60 , "schools" , "1990" );
        //line_chart_dataset.addValue( 120 , "schools" , "2000" );
        //line_chart_dataset.addValue( 240 , "schools" , "2010" );
        //line_chart_dataset.addValue( 300 , "schools" , "2014" );
        //JFreeChart lineChartObject = ChartFactory.createLineChart(
        //        "Schools Vs Years","Year",
        //        "Schools Count",
        //        line_chart_dataset, PlotOrientation.VERTICAL,
        //        true,true,false);
        //int width = 640;    /* Width of the image */
        //int height = 480;   /* Height of the image */
        //File lineChart = new File( "LineChart.jpeg" );
        //try
        //{
        //    ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
        //} catch (IOException e)
        //{
        //    e.printStackTrace();
        //}


        try
        {
            InputArgumentParser inputPaser = new InputArgumentParser(args);

            DoubleMatlabManager manager = new DoubleMatlabManager();
            manager.openSession();

            manager.setPath(System.getProperty("user.dir"));
            manager.setPath(inputPaser.getPath());


            String fileName = inputPaser.getFileName();
            IPopulationLogger<DoubleSolution> logger;
            if(fileName==null || fileName.equals(""))
            {
                logger = new NullDoubleLogger();
            }
            else
            {
                logger = new FileDoubleLogger(inputPaser.getFileName(), inputPaser.getFront());
            }


            DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ScriptRunner")
                    .setNumberOfVariables(inputPaser.getNumberOfVariables())
                    .setNumberOfObjectives(inputPaser.getNumberOfObjectives())
                    .setProblemName(inputPaser.getName())
                    .addLimits(inputPaser.getLimits())
                    .addLogger(logger);

            //TODO
            //PAES


            ExternalDoubleProblem p = builder.build();

            Algorithm<List<DoubleSolution>> algorithm = inputPaser.getAlgorithm(p);

            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();


            logger.addHeaderInfo(inputPaser.getMetaData());

            logger.save();

            System.out.println(algorithm.getResult());
            System.out.println(algorithmRunner.getComputingTime());
            System.out.println(p.getName());


            manager.setPath(System.getProperty("user.dir"));
            manager.closeSession();


        }
        catch (ParseException e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }




    }

    //public static void T3()
    //{
    //    DoubleMatlabManager manager = new DoubleMatlabManager();
    //    manager.openSession();
    //    manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
    //    DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "Schaffer")
    //            .setNumberOfVariables(1)
    //            .setNumberOfObjectives(2)
    //            .addLimit(-10,10)
    //            .setProblemName("Schaffer");
    //    ExternalDoubleProblem p = builder.build();
    //    Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(p,
    //            new SBXCrossover(0.9, 20),
    //            new PolynomialMutation(0.2, 20))
    //            .setPopulationSize(60)
    //            .setMaxEvaluations(2500)
    //            .build();
    //    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
    //    List<DoubleSolution> population = algorithm.getResult();
    //    for(DoubleSolution sl : population)
    //    {
    //        System.out.println("["+ sl.getObjective(0) + " " + sl.getObjective(1) + "];");
    //    }
    //    manager.closeSession();
    //}

    //public static void T2()
    //{
    //    BinaryMatlabManager manager = new BinaryMatlabManager();
    //    manager.openSession();
    //    manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
    //    BinaryProblemBuilder builder = new BinaryProblemBuilder(manager, "Knapsack")
    //            .startArray()
    //            .addValue(5)
    //            .addValue(3)
    //            .addValue(7)
    //            .addValue(2)
    //            .addValue(3)
    //            .addValue(3)
    //            .stopArray()
    //            .addValue(11)
    //            .setNumberOfObjectives(2)
    //            .setNumberOfVariables(6);
    //    ExternalBinaryProblem p = builder.build();
    //    Algorithm<BinarySolution> algorithm1 = new NSGAII(p, 600, 20,
    //            new SinglePointCrossover(0.5),
    //            new BitFlipMutation(0.5),
    //            new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()), new SequentialSolutionListEvaluator());
    //    AlgorithmRunner algorithmRunner1 = new AlgorithmRunner.Executor(algorithm1).execute();
    //    System.out.println(algorithm1.getResult());
    //    System.out.println(p.getName());
    //    manager.closeSession();
    //}

    //public static void T1()
    //{
    //    DoubleMatlabManager manager = new DoubleMatlabManager();
    //    manager.openSession();
    //    manager.setPath("C:\\Users\\gasper\\Desktop\\jMetalMatlabWrapper\\matlabscripts");
    //    DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ZagonTest")
    //            .setNumberOfVariables(4)
    //            .setNumberOfObjectives(10)
    //            .setProblemName("testName")
    //            .addLimit(-100, 100)
    //            .addLimit(-100, 100)
    //            .addLimit(-100, 100)
    //            .addLimit(-100, 100);
    //    ExternalDoubleProblem p = builder.build();
    //    Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(p,
    //            new SBXCrossover(0.9, 20),
    //            new PolynomialMutation(0.2, 20))
    //            .setPopulationSize(60)
    //            .setMaxEvaluations(1000)
    //            .build();
    //    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
    //    System.out.println(algorithm.getResult());
    //    System.out.println(algorithmRunner.getComputingTime());
    //    System.out.println(p.getName());
    //    manager.closeSession();
    //}


}