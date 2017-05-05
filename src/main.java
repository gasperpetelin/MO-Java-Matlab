import ConnectionManager.Implementations.DoubleMatlabManager;
import InputParser.InputArgumentParser;
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



            ExternalDoubleProblem p = builder.build();
            Algorithm<List<DoubleSolution>> algorithm = inputPaser.getAlgorithm(p);
            logger.addHeaderInfo(inputPaser.getMetaData());
            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

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
}