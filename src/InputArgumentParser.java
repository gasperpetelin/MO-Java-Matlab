import Problems.ExternalDoubleProblem;
import Problems.Limit;
import Problems.PopulationLogger.AlgorithmMetaData;
import org.apache.commons.cli.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.*;
import org.uma.jmetal.operator.impl.mutation.*;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class InputArgumentParser
{

    final String swNumberOfVariables = "v"; final String fnNumberOfVariables = "variables";


    boolean output = true;
    CommandLine cmd;
    AlgorithmMetaData metaData;

    public void write(String message)
    {
        if(output)
            System.out.println(message);
    }

    public InputArgumentParser(String[] input) throws ParseException
    {
        this.output = true;

        Options options = new Options();

        Option numberOfVariables = new Option(swNumberOfVariables, fnNumberOfVariables, true, "number of variables");
        numberOfVariables.setRequired(true);
        options.addOption(numberOfVariables);

        Option numberOfObjectives = new Option("o", "objectives", true, "number of objectives");
        numberOfObjectives.setRequired(true);
        options.addOption(numberOfObjectives);

        Option populationSize = new Option("pop", "population", true, "population size");
        populationSize.setRequired(false);
        options.addOption(populationSize);

        Option numberOfIterations = new Option("eval", "evaluations", true, "number of evaluations");
        numberOfIterations.setRequired(false);
        options.addOption(numberOfIterations);

        Option problemName = new Option("name", "name", true, "problem name");
        problemName.setRequired(false);
        options.addOption(problemName);

        Option problemPath = new Option("p", "path", true, "path to problem directory");
        problemPath.setRequired(false);
        options.addOption(problemPath);

        Option minLimit = new Option("minL", "minL", true, "minimum limits");
        minLimit.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(minLimit);

        Option maxLimit = new Option("maxL", "maxL", true, "maximum limits");
        maxLimit.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(maxLimit);

        Option maxLimitA = new Option("maxLA", "maxLA", true, "maximum limit of all variables");
        maxLimitA.setRequired(false);
        options.addOption(maxLimitA);

        Option minLimitA = new Option("minLA", "minLA", true, "minimum limit of all variables");
        minLimitA.setRequired(false);
        options.addOption(minLimitA);

        Option crossover = new Option("cross", "crossover", true, "crossover algorithm");
        crossover.setRequired(false);
        crossover.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(crossover);

        Option mutation = new Option("mut", "mutation", true, "mutation algorithm");
        mutation.setRequired(false);
        mutation.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(mutation);

        Option algorithm = new Option("algo", "algorithm", true, "optimization algorithm");
        algorithm.setRequired(false);
        options.addOption(algorithm);

        Option print = new Option("print", "print", true, "print all warnings");
        print.setRequired(false);
        options.addOption(print);

        Option file = new Option("file", "file", true, "write to file");
        file.setRequired(false);
        options.addOption(file);

        Option front = new Option("front", "front", true, "front number");
        front.setRequired(false);
        options.addOption(front);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();


        cmd = parser.parse(options, input);


        this.output = this.writeToConsole();

    }

    private boolean writeToConsole() throws ParseException
    {
        String var = cmd.getOptionValue("print");
        if(var == null)
            return true;

        switch (var)
        {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new ParseException("parameter -print should be 0 or 1");
        }
    }

    public int getNumberOfVariables() throws ParseException
    {
        return this.positiveIntEval(cmd.getOptionValue(fnNumberOfVariables), "v");
    }

    private int positiveIntEval(String evalStr, String fieldName) throws ParseException
    {
        ParseException ex = new ParseException("parameter -" + fieldName + " should be positive integer");
        try
        {
            int i =  Integer.parseInt(evalStr);
            if(i<1)
                throw ex;
            return i;
        }
        catch (NumberFormatException e)
        {
            throw ex;
        }
    }

    public int getNumberOfObjectives() throws ParseException
    {
        return this.positiveIntEval(cmd.getOptionValue("objectives"), "-o");
    }

    public Integer getFront() throws ParseException
    {
        ParseException ex = new ParseException("parameter -front should be nonnegative integer");
        try
        {
            int i =  Integer.parseInt(cmd.getOptionValue("front"));
            if(i<0)
                throw ex;
            return i;
        }
        catch (NumberFormatException e)
        {
           return null;
        }
    }

    public List<Limit> getLimits() throws ParseException
    {
        try
        {
            String[] lower = cmd.getOptionValues("minL");
            String[] upper = cmd.getOptionValues("maxL");
            if (lower != null && upper != null)
            {
                List<Limit> limits = new ArrayList<>();
                String[] lowerLimits = lower;
                String[] upperLimits = upper;
                for (int i = 0; i < lower.length; i++)
                {
                    double low = Double.parseDouble(lowerLimits[i]);
                    double up = Double.parseDouble(upperLimits[i]);
                    limits.add(new Limit(low, up));
                }
                return limits;
            }

            String minA = cmd.getOptionValue("minLA");
            String maxA = cmd.getOptionValue("maxLA");

            if (minA != null && !minA.isEmpty() && maxA != null && !maxA.isEmpty())
            {
                int variables = this.getNumberOfVariables();
                List<Limit> limits = new ArrayList<>();
                for (int i = 0; i < variables; i++)
                {
                    limits.add(new Limit(Double.parseDouble(minA), Double.parseDouble(maxA)));
                }
                return limits;
            }
        }
        catch (NumberFormatException ex)
        {
            throw new ParseException("Limits not valid double");
        }
        this.write("Limits not set. (-100, 100) will be used.");
        List<Limit> limits = new ArrayList<>();
        for (int i = 0; i < this.getNumberOfVariables() ; i++)
        {
            limits.add(new Limit(-100, 100));
        }
        return limits;
    }

    public CrossoverOperator<DoubleSolution> getCrossoverOperator() throws ParseException
    {
        return CrossoverFactory(cmd.getOptionValues("cross"));
    }

    public int getPopulationSize()
    {
        String var = cmd.getOptionValue("population");
        if(var == null)
        {
            int size  = 100;
            this.write("Population size not set. Default value is " + size + ".");
            return size;

        }
        return Integer.parseInt(var);
    }

    public MutationOperator<DoubleSolution> getMutation()
    {
        return this.MutationFactory(cmd.getOptionValues("mut"));
    }


    private MutationOperator<DoubleSolution> MutationFactory(String[] parameters)
    {
        if(parameters != null && parameters.length != 0)
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

            }
        }
        this.write("Mutation operator not set. NullMutation will be used.");
        return new NullMutation<>();
    }



    private CrossoverOperator<DoubleSolution> CrossoverFactory(String[] parameters) throws ParseException
    {

        if(parameters != null && parameters.length != 0)
        {
            String crossoverType = parameters[0].toLowerCase();
            switch (crossoverType)
            {
                case "blxalpha":
                    if (parameters.length == 3)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        double alpha = Double.parseDouble(parameters[2]);
                        return new BLXAlphaCrossover(crossoverProbability, alpha);
                    }
                    if (parameters.length == 2)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        return new BLXAlphaCrossover(crossoverProbability);
                    }
                    break;

                case "null":
                    return new NullCrossover<>();

                case "sbx":
                    if (parameters.length == 3)
                    {
                        double crossoverProbability = Double.parseDouble(parameters[1]);
                        double distributionIndex = Double.parseDouble(parameters[2]);
                        return new SBXCrossover(crossoverProbability, distributionIndex);
                    }
                    break;
                default:
                    throw new ParseException("No crossover operation with name: " + crossoverType);
            }
        }
        this.write("Crossover operator not set. NullCrossover will be used.");
        return new NullCrossover<>();
    }

    public int getNumberOfEvaluations() throws ParseException
    {
        String var = cmd.getOptionValue("evaluations");
        if (var == null)
        {
            int size = 2500;
            this.write("Number of evaluations not set. Default value is " + size + ".");
            return size;
        }

        ParseException ex = new ParseException("parameter -eval should be positive integer");

        try
        {
            int i = Integer.parseInt(var);
            if(i<1)
                throw ex;
            return i;
        }
        catch (NumberFormatException e)
        {
            throw ex;
        }

    }

    public String getName()
    {
        String name =  cmd.getOptionValue("name");
        if(name == null)
            return "No name set";
        return name;
    }

    public String getFileName()
    {
        return cmd.getOptionValue("file");
    }

    public String getPath()
    {
        String name =  cmd.getOptionValue("path");
        if(name == null)
            return "";
        return name;
    }

    public AlgorithmMetaData getMetaData() throws Exception
    {
        if(this.metaData == null)
            throw new Exception("Algorithm not yet build");

        return this.metaData;

    }

    public Algorithm<List<DoubleSolution>> getAlgorithm(ExternalDoubleProblem problem) throws ParseException
    {
        return AlgorithmFactory(problem, cmd.getOptionValue("algo"));
    }

    private Algorithm<List<DoubleSolution>> AlgorithmFactory(ExternalDoubleProblem problem, String type) throws ParseException
    {


        CrossoverOperator<DoubleSolution> cross = this.getCrossoverOperator();
        MutationOperator<DoubleSolution> mut = this.getMutation();
        Algorithm<List<DoubleSolution>> algorithm = null;

        int maxeval = this.getNumberOfEvaluations();
        int popSize = this.getPopulationSize();
        if(type != null)
        {
            switch (type)
            {
                case "nsgaii":
                    algorithm = new NSGAIIBuilder<>(problem, cross, mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "ibea":
                    algorithm = new IBEABuilder(problem)
                            .setArchiveSize(5)
                            .setCrossover(cross)
                            .setMutation(mut)
                            .setMaxEvaluations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "random":
                    algorithm = new RandomSearchBuilder(problem)
                            .setMaxEvaluations(maxeval)
                            .build();
                    break;
                case "gde3":
                    algorithm = new GDE3Builder(problem)
                            .setPopulationSize(popSize)
                            .setMaxEvaluations(maxeval)
                            .build();
                    break;
                case "spea2":
                    algorithm = new SPEA2Builder(problem, cross, mut)
                            .setMaxIterations(maxeval)
                            .setPopulationSize(popSize)
                            .build();
                    break;
                case "paes":
                    algorithm = new PAESBuilder(problem)
                            .setMutationOperator(mut)
                            .setMaxEvaluations(maxeval)
                            .build();
                    break;


                default:
                    throw new ParseException("No algorithm with name: " + type);
            }
        }


        if(algorithm==null)
            algorithm = new NSGAIIBuilder<>(problem, cross, mut).build();

        this.metaData = new AlgorithmMetaData(algorithm, mut, cross);

        return algorithm;
    }
}
