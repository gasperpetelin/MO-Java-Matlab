package InputParser;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class InputOptions
{
    public static final String swNumberOfVariables = "v"; public static final String fnNumberOfVariables = "variables";

    public static Options getOptions()
    {
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

        return options;
    }
}
