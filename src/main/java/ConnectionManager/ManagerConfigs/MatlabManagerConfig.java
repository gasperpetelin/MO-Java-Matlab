package ConnectionManager.ManagerConfigs;


public abstract class MatlabManagerConfig
{
    private String variableName = "variable123";
    private String evaluateMethod = "evaluate";
    private String numberOfVariables = "NumberOfVariables";
    private String numberOfObjectives = "NumberOfObjectives";
    private String problemName = "Name";

    public String getNumberOfVariables()
    {
        return numberOfVariables;
    }

    public void setNumberOfVariables(String numberOfVariables)
    {
        this.numberOfVariables = numberOfVariables;
    }

    public String getNumberOfObjectives()
    {
        return numberOfObjectives;
    }

    public void setNumberOfObjectives(String numberOfObjectives)
    {
        this.numberOfObjectives = numberOfObjectives;
    }

    public String getEvaluateMethod()
    {
        return evaluateMethod;
    }

    public void setEvaluateMethod(String evaluateMethod)
    {
        this.evaluateMethod = evaluateMethod;
    }


    public String getProblemName()
    {
        return problemName;
    }

    public void setProblemName(String variableName)
    {
        this.problemName = variableName;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }





}
