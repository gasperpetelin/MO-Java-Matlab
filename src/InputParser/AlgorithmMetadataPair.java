package InputParser;


import Problems.PopulationLogger.AlgorithmMetaData;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;

public class AlgorithmMetadataPair
{
    private Algorithm<List<DoubleSolution>> algorithm;
    private AlgorithmMetaData meta;

    public Algorithm<List<DoubleSolution>> getAlgorithm()
    {
        return algorithm;
    }

    public AlgorithmMetaData getMeta()
    {
        return meta;
    }

    public AlgorithmMetadataPair(Algorithm<List<DoubleSolution>> algorithm, AlgorithmMetaData meta)
    {
        this.algorithm = algorithm;
        this.meta = meta;
    }






}
