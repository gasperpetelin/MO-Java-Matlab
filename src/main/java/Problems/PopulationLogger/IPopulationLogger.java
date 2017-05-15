package Problems.PopulationLogger;


import org.uma.jmetal.solution.Solution;

public interface IPopulationLogger<T extends Solution<?>>
{
    void init(int numberOfVariables, int numberOfObjectives);
    void logSolution(T solution);
    void addHeaderInfo(IAlgorithmInfo info);
    void save();
}
