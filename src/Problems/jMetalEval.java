package Problems;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.List;

public class jMetalEval<S extends Solution<?>> implements SolutionListEvaluator<S>
{
    private int numberOfThreads;

    public jMetalEval(int numberOfThreads, Problem<S> problem) {
        if(numberOfThreads == 0) {
            this.numberOfThreads = Runtime.getRuntime().availableProcessors();
        } else {
            this.numberOfThreads = numberOfThreads;
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "" + this.numberOfThreads);
        }

        //JMetalLogger.logger.info("Number of cores: " + numberOfThreads);
    }

    public List<S> evaluate(List<S> solutionList, Problem<S> problem) {
        if(problem instanceof ConstrainedProblem) {
            solutionList.parallelStream().forEach((s) -> {
                problem.evaluate(s);
                ((ConstrainedProblem)problem).evaluateConstraints(s);
            });
        } else {
            solutionList.parallelStream().forEach((s) -> {
                problem.evaluate(s);
            });
        }

        return solutionList;
    }

    public int getNumberOfThreads() {
        return this.numberOfThreads;
    }

    public void shutdown() {
    }
}