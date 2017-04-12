package CommunicationManager;


import MatlabVariableTransformations.AbstractMatlabVariables;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public interface ICommandManager
{
    void openSession() throws MatlabConnectionException;
    void closeSession();
    void executeCommand(String command) throws MatlabInvocationException;
    boolean isSessionOpen();
    void newObject(String variable, String objectName, String parameters) throws MatlabInvocationException;
    void setVariable(String name, AbstractMatlabVariables value) throws MatlabInvocationException;
    <T extends AbstractMatlabVariables> T getVariable(String name, Class<T> newClass) throws MatlabInvocationException, IllegalAccessException, InstantiationException, MatlabConnectionException;
    void setProblemPath(String path) throws MatlabInvocationException;
    Object getVariable(String name);
    double[][] get2DArray(String name);
    ConstraintEvaluation getConstraint(String objectName, String functionName, String arguments);

}
