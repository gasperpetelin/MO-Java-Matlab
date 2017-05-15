package ConnectionManager.ManagerInterfaces;

import org.uma.jmetal.solution.Solution;

public interface IConstrainEvaluation<T extends Solution<?>> extends ISolutionEvaluation<T>
{
    ConstraintEvaluation getConstraint(T solution);
}