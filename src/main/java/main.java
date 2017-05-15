import ConnectionManager.CommandExecutionException;
import ConnectionManager.Implementations.DoubleMatlabManager;
import InputParser.InputArgumentParser;
import Problems.ExternalDoubleProblem;
import Problems.PopulationLogger.FileDoubleLogger;
import Problems.PopulationLogger.FileDoubleRealTimeLogger;
import Problems.PopulationLogger.IPopulationLogger;
import Problems.PopulationLogger.NullDoubleLogger;
import Problems.ProblemBuilders.DoubleProblemBuilder;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.problem.multiobjective.Kursawe;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
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
                String[] arr = fileName.split("\\.(?=[^\\.]+$)");
                if(arr.length!=2)
                    throw new CommandExecutionException("Incorrect file name format.");
                if(inputPaser.getContinuousLogging())
                    logger = new FileDoubleRealTimeLogger(arr[0], arr[1], inputPaser.getFront());
                else
                    logger = new FileDoubleLogger(arr[0], arr[1], inputPaser.getFront());
            }


            DoubleProblemBuilder builder = new DoubleProblemBuilder(manager, "ScriptRunner")
                    .setNumberOfVariables(inputPaser.getNumberOfVariables())
                    .setNumberOfObjectives(inputPaser.getNumberOfObjectives())
                    .setProblemName(inputPaser.getName())
                    .addLimits(inputPaser.getLimits());

            if(!inputPaser.saveOnlyResult())
                builder.addLogger(logger);

            ExternalDoubleProblem p = builder.build();
            Algorithm<List<DoubleSolution>> algorithm = inputPaser.getAlgorithm(p);
            logger.addHeaderInfo(inputPaser.getMetaData());
            new AlgorithmRunner.Executor(algorithm).execute();


            if(inputPaser.saveOnlyResult())
            {
                logger.init(inputPaser.getNumberOfVariables(), inputPaser.getNumberOfObjectives());
                for (DoubleSolution s : algorithm.getResult())
                {
                    logger.logSolution(s);
                }
            }

            logger.save();

            manager.setPath(System.getProperty("user.dir"));
            manager.closeSession();


        }
        catch (ParseException e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}