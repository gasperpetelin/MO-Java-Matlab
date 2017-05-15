package ConnectionManager;

public interface IManager
{
    void openSession() throws CommandExecutionException;
    void closeSession();
    void newObject(String objectName, String parameters) throws CommandExecutionException;
    void setPath(String path) throws CommandExecutionException;
    int getNumberOfObjectives() throws CommandExecutionException;
    int getNumberOfVariables() throws CommandExecutionException;
    String getProblemName() throws CommandExecutionException;
}
