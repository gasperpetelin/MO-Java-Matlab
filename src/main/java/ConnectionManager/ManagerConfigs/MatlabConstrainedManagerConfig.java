package ConnectionManager.ManagerConfigs;

public abstract class MatlabConstrainedManagerConfig extends MatlabManagerConfig
{
    private String objectiveMethod = "evaluateConstraints";
    private String numberOfConstraints = "NumberOfConstraints";

    public String getObjectiveMethod()
    {
        return objectiveMethod;
    }

    public void setObjectiveMethod(String objectiveMethod)
    {
        this.objectiveMethod = objectiveMethod;
    }

    public String getNumberOfConstraints()
    {
        return numberOfConstraints;
    }

    public void setNumberOfConstraints(String numberOfConstraints)
    {
        this.numberOfConstraints = numberOfConstraints;
    }



}
