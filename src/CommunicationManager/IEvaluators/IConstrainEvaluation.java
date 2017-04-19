package CommunicationManager.IEvaluators;


import CommunicationManager.Implementations.ConstraintEvaluation;
import org.uma.jmetal.solution.Solution;

public interface IConstrainEvaluation<T extends Solution<?>> extends ISolutionEvaluation<T>
{
    ConstraintEvaluation getConstraint(T solution);
}
