package Problems;


import ConnectionManager.ManagerInterfaces.ISolutionEvaluation;
import Problems.PopulationLogger.IPopulationLogger;
import Problems.PopulationLogger.NullDoubleLogger;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExternalDoubleProblem extends AbstractDoubleProblem
{
    IPopulationLogger<DoubleSolution> logger;

    ISolutionEvaluation<DoubleSolution> evaluator;

    public ExternalDoubleProblem(ISolutionEvaluation<DoubleSolution> evaluator, String problemName, int numberOfVariables, int numberOfObjectives, List<Limit> limits)
    {
        this(evaluator, problemName, numberOfVariables, numberOfObjectives, limits, new NullDoubleLogger());
    }

    public ExternalDoubleProblem(ISolutionEvaluation<DoubleSolution> evaluator, String problemName, int numberOfVariables, int numberOfObjectives, List<Limit> limits, IPopulationLogger<DoubleSolution> logger)
    {
        this.evaluator = evaluator;

        this.setNumberOfVariables(numberOfVariables);
        this.setNumberOfObjectives(numberOfObjectives);
        this.setName(problemName);

        List<Double> lowerLimit = new ArrayList(this.getNumberOfVariables());
        List<Double> upperLimit = new ArrayList(this.getNumberOfVariables());

        for(int i = 0; i < this.getNumberOfVariables(); ++i) {
            lowerLimit.add(limits.get(i).getLower());
            upperLimit.add(limits.get(i).getUpper());
        }

        this.setLowerLimit(lowerLimit);
        this.setUpperLimit(upperLimit);

        this.logger = logger;
        this.logger.init(getNumberOfVariables(), getNumberOfObjectives());
    }


    @Override
    public void evaluate(DoubleSolution solution)
    {
        double[] objectives = evaluator.getSolution(solution);
        if(objectives.length<this.getNumberOfObjectives())
            throw new JMetalException("Evaluation function should return at least " + this.getNumberOfObjectives() + " objectives.");



        for (int i = 0; i < this.getNumberOfObjectives(); i++)
        {
            solution.setObjective(i, objectives[i]);
        }

        this.logger.logSolution(solution);

    }
}
