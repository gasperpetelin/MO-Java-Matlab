import Problems.Limit;
import org.apache.commons.cli.*;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.*;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class InputArgumentParser
{
    boolean output = true;
    CommandLine cmd;

    public void write(String message)
    {
        if(output)
            System.out.println(message);
    }

    public InputArgumentParser(String[] input, boolean writeToConsole)
    {
        this.output = writeToConsole;

        Options options = new Options();

        Option numberOfVariables = new Option("v", "variables", true, "number of variables");
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

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();



        try
        {
            cmd = parser.parse(options, input);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

    }

    public int getNumberOfVariables()
    {
        String var = cmd.getOptionValue("variables");
        if(var == null)
            return -1;
        return Integer.parseInt(cmd.getOptionValue("variables"));
    }

    public int getNumberOfObjectives()
    {
        String var = cmd.getOptionValue("objectives");
        if(var == null)
            return -1;
        return Integer.parseInt(cmd.getOptionValue("objectives"));
    }

    public List<Limit> getLimits()
    {

        String[] lower  = cmd.getOptionValues("minL");
        String[] upper = cmd.getOptionValues("maxL");
        if(lower != null && upper != null)
        {
            List<Limit> limits = new ArrayList<>();
            String[] lowerLimits = lower;
            String[] upperLimits = upper;
            for (int i = 0; i < lower.length ; i++)
            {
                double low = Double.parseDouble(lowerLimits[i]);
                double up = Double.parseDouble(upperLimits[i]);
                limits.add(new Limit(low, up));
            }
            return limits;
        }

        String minA = cmd.getOptionValue("minLA");
        String maxA = cmd.getOptionValue("maxLA");

        if(minA != null && !minA.isEmpty() && maxA != null && !maxA.isEmpty())
        {
            int variables = this.getNumberOfVariables();
            List<Limit> limits = new ArrayList<>();
            for (int i = 0; i < variables; i++)
            {
                limits.add(new Limit(Double.parseDouble(minA), Double.parseDouble(maxA)));
            }
            return limits;
        }

        this.write("Limits not set. (-100, 100) will be used.");
        List<Limit> limits = new ArrayList<>();
        for (int i = 0; i < this.getNumberOfVariables() ; i++)
        {
            limits.add(new Limit(-100, 100));
        }
        return limits;
    }

    public CrossoverOperator<DoubleSolution> getCrossoverOperator()
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

    private CrossoverOperator<DoubleSolution> CrossoverFactory(String[] parameters)
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
            }
        }
        this.write("Crossover operator not set. NullCrossover will be used.");
        return new NullCrossover<>();
    }

    public int getNumberOfEvaluations()
    {
        String var = cmd.getOptionValue("evaluations");
        if(var == null)
        {
            int size  = 2500;
            this.write("Number of evaluations not set. Default value is " + size + ".");
            return size;

        }
        return Integer.parseInt(var);
    }

    public String getName()
    {
        String name =  cmd.getOptionValue("name");
        if(name == null)
            return "No name set";
        return name;
    }

    public String getPath()
    {
        String name =  cmd.getOptionValue("path");
        if(name == null)
            return "";
        return name;
    }
}
