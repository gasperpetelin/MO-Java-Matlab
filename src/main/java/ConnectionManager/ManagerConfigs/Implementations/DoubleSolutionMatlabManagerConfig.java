package ConnectionManager.ManagerConfigs.Implementations;

import ConnectionManager.ManagerConfigs.MatlabManagerConfig;

public class DoubleSolutionMatlabManagerConfig extends MatlabManagerConfig
{
    private String variableLimits = "Limits";

    public String getVariableLimits()
    {
        return variableLimits;
    }

    public void setVariableLimits(String variableLimits)
    {
        this.variableLimits = variableLimits;
    }
}
