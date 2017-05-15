package Problems.PopulationLogger;

import org.uma.jmetal.solution.DoubleSolution;

public class NullDoubleLogger implements IPopulationLogger<DoubleSolution>
{


    @Override
    public void init(int numberOfVariables, int numberOfObjectives){}

    @Override
    public void logSolution(DoubleSolution solution){}

    @Override
    public void addHeaderInfo(IAlgorithmInfo info){}

    @Override
    public void save(){}
}
