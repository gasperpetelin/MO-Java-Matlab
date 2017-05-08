package InputParser;

import Problems.ExternalDoubleProblem;
import Problems.Limit;
import Problems.PopulationLogger.AlgorithmMetaData;
import org.apache.commons.cli.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class InputArgumentParser
{

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
        Options options = InputOptions.getOptions();
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

    public boolean saveOnlyResult() throws ParseException
    {
        String var = cmd.getOptionValue("result");
        if(var == null)
            return true;

        switch (var)
        {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new ParseException("parameter -r should be 0 or 1");
        }
    }

    public int getNumberOfVariables() throws ParseException
    {
        return this.positiveIntEval(cmd.getOptionValue(InputOptions.fnNumberOfVariables), "v");
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
        CrossoverOperator<DoubleSolution> co = CrossoverFactory.getCrossover(cmd.getOptionValues("cross"));
        if(co==null)
        {
            this.write("Crossover operator not set. NullCrossover will be used.");
            return new NullCrossover<>();
        }
        return co;
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

    public MutationOperator<DoubleSolution> getMutation() throws ParseException
    {
        MutationOperator<DoubleSolution> opr = MutationFactory.getMutation(cmd.getOptionValues("mut"));
        if(opr==null)
        {
            this.write("Mutation operator not set. NullMutation will be used.");
            return new NullMutation<>();
        }
        return opr;

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
        CrossoverOperator<DoubleSolution> cross = this.getCrossoverOperator();
        MutationOperator<DoubleSolution> mut = this.getMutation();
        int maxeval = this.getNumberOfEvaluations();
        int popSize = this.getPopulationSize();

        AlgorithmMetadataPair pair = AlgorithmFactory.getAlgorithm(cmd.getOptionValue("algo"), problem, cross, mut, maxeval, popSize);

        Algorithm<List<DoubleSolution>> algo = null;

        if(pair==null)
        {
            algo = new NSGAIIBuilder<>(problem, cross, mut).setPopulationSize(popSize).setMaxEvaluations(maxeval).build();
            this.metaData = new AlgorithmMetaData(algo, mut, cross, maxeval, popSize);
        }
        else
        {
            algo = pair.getAlgorithm();
            this.metaData = pair.getMeta();
        }
        this.metaData.setAlgorithm(algo);
        return algo;
    }

}
