package ConnectionManager;

public interface IManager
{
    void openSession();
    void closeSession();
    void newObject(String objectName, String parameters);
    void setPath(String path);
    int getNumberOfObjectives();
    int getNumberOfVariables();
    String getProblemName();
}
