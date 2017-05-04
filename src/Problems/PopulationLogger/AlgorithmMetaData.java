package Problems.PopulationLogger;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class AlgorithmMetaData implements IHeaderInfo
{
    MutationOperator<DoubleSolution> mutation;
    CrossoverOperator<DoubleSolution> cross;
    Algorithm<List<DoubleSolution>> algo;

    public AlgorithmMetaData(Algorithm<List<DoubleSolution>> algo,
                             MutationOperator<DoubleSolution> mutationParm,
                             CrossoverOperator<DoubleSolution> crossParm)
    {
        this.mutation = mutationParm;
        this.cross = crossParm;
        this.algo = algo;
    }

    @Override
    public String getHeaderDetails()
    {
        return this.algo.getClass().getSimpleName() + "," +
                this.mutation.getClass().getSimpleName() + "," +
                this.cross.getClass().getSimpleName();
    }
}
