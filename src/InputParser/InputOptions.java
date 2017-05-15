package InputParser;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class InputOptions
{
    public static final String swNumberOfVariables = "v"; public static final String fnNumberOfVariables = "variables";
    public static final String swNumberOfObjectives = "o"; public static final String fnNumberOfObjectives = "objectives";
    public static final String swPopulationSize = "pop"; public static final String fnPopulationSize = "population";
    public static final String swNumberOfEvaluations = "eval"; public static final String fnNumberOfEvaluations = "evaluations";
    public static final String swProblemName = "name"; public static final String fnProblemName = "name";

    public static Options getOptions()
    {
        Options options = new Options();

        Option numberOfVariables = new Option(swNumberOfVariables, fnNumberOfVariables, true, "number of variables");
        numberOfVariables.setRequired(true);
        options.addOption(numberOfVariables);

        Option numberOfObjectives = new Option(swNumberOfObjectives, fnNumberOfObjectives, true, "number of objectives");
        numberOfObjectives.setRequired(true);
        options.addOption(numberOfObjectives);

        options.addOption(new Option(swPopulationSize, fnPopulationSize, true, "population size"));
        options.addOption(new Option(swNumberOfEvaluations, fnNumberOfEvaluations, true, "number of evaluations"));
        options.addOption(new Option(swProblemName, fnProblemName, true, "problem name"));


        Option problemPath = new Option("p", "path", true, "path to problem directory");
        options.addOption(problemPath);

        Option minLimit = new Option("minL", "minL", true, "minimum limits");
        minLimit.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(minLimit);

        Option maxLimit = new Option("maxL", "maxL", true, "maximum limits");
        maxLimit.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(maxLimit);

        Option maxLimitA = new Option("maxLA", "maxLA", true, "maximum limit of all variables");
        options.addOption(maxLimitA);

        Option minLimitA = new Option("minLA", "minLA", true, "minimum limit of all variables");
        options.addOption(minLimitA);

        Option crossover = new Option("cross", "crossover", true, "crossover algorithm");
        crossover.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(crossover);

        Option mutation = new Option("mut", "mutation", true, "mutation algorithm");
        mutation.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(mutation);

        Option algorithm = new Option("algo", "algorithm", true, "optimization algorithm");
        options.addOption(algorithm);

        Option print = new Option("print", "print", true, "print all warnings");
        options.addOption(print);

        Option file = new Option("file", "file", true, "write to file");
        options.addOption(file);

        Option front = new Option("front", "front", true, "front number");
        options.addOption(front);

        Option onlyResult = new Option("r", "result", true, "save only result");
        options.addOption(onlyResult);

        Option conlog = new Option("con", "con", false, "continuous writing to file");
        options.addOption(conlog);

        return options;
    }
}
