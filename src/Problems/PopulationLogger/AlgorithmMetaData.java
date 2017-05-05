package Problems.PopulationLogger;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class AlgorithmMetaData implements IAlgorithmInfo
{
    MutationOperator<DoubleSolution> mutation;
    CrossoverOperator<DoubleSolution> cross;
    Algorithm<List<DoubleSolution>> algo;
    int pop;
    int eval;

    public AlgorithmMetaData(Algorithm<List<DoubleSolution>> algo,
                             MutationOperator<DoubleSolution> mutationParm,
                             CrossoverOperator<DoubleSolution> crossParm,
                             int evaluations, int populationSize)
    {
        this.mutation = mutationParm;
        this.cross = crossParm;
        this.algo = algo;
        this.pop = populationSize;
        this.eval = evaluations;
    }

    public void setAlgorithm(Algorithm<List<DoubleSolution>> algo)
    {
        this.algo = algo;
    }

    @Override
    public String getHeaderDetails()
    {
        return this.algo.getClass().getSimpleName() + "," +
                this.mutation.getClass().getSimpleName() + "," +
                this.cross.getClass().getSimpleName();
    }

    @Override
    public int getNumberOfEvaluations()
    {
        return this.eval;
    }

    @Override
    public int getPopulationSize()
    {
        return this.pop;
    }
}
