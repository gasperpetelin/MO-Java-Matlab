package ExternalProblems.Abstractions;


import CommunicationManager.ICommandManager;
import matlabcontrol.MatlabConnectionException;
import org.uma.jmetal.problem.impl.AbstractGenericProblem;
import org.uma.jmetal.solution.Solution;

public abstract class AbstractExternalGenericProblem<S extends Solution<?>> extends AbstractGenericProblem<S>
{
    protected ICommandManager manager;
    protected String nameOfObjectVariable;
    public  AbstractExternalGenericProblem(ICommandManager manager, String nameOfObjectVariable)
    {
        this.manager = manager;
        this.nameOfObjectVariable = nameOfObjectVariable;
    }

    public void openSession() throws MatlabConnectionException
    {
        this.manager.openSession();
    }

    public void closeSession()
    {
        this.manager.closeSession();
    }
}
