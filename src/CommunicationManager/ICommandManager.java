package CommunicationManager;


import CommunicationManager.Fields.IFieldGetters;
import CommunicationManager.Fields.IFieldSetters;
import CommunicationManager.Implementations.ConstraintEvaluation;
import CommunicationManager.Implementations.Limit;
import MatlabVariableTransformations.AbstractMatlabVariables;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public interface ICommandManager extends IFieldSetters, IFieldGetters
{
    void openSession() throws MatlabConnectionException;
    void closeSession();
    boolean isSessionOpen();
    void newObject(String objectName, String parameters) throws MatlabInvocationException;
    void setProblemPath(String path) throws MatlabInvocationException;
    ConstraintEvaluation getConstraint(String arguments);
    <T extends AbstractMatlabVariables> T getVariable(String name, Class<T> newClass) throws MatlabInvocationException, IllegalAccessException, InstantiationException, MatlabConnectionException;
    double[] getSolution(String objectName, String arguments, int numberOfObjectives);
    void setObjectName(String name);
    String getProblemName();
    Limit[] getLimits(int numberOfVariables);
    int getNumberOfBits();
    void setBitsPerVariableFieldName(String name);

}
