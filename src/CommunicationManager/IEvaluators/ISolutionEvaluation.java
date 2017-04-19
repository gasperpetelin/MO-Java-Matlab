package CommunicationManager.IEvaluators;


import org.uma.jmetal.solution.Solution;

public interface ISolutionEvaluation<T extends Solution<?>>
{
    double[] getSolution(T solution);
    void sessionOpen();
    void sessionClose();
}
