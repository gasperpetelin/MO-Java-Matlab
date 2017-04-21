package ConnectionManager.ManagerConfigs.Implementations;

import ConnectionManager.ManagerConfigs.MatlabManagerConfig;

public class BinarySolutionMatlabManagerConfig extends MatlabManagerConfig
{
    private String numberOfBitsPerVariable = "BitsPerVariable";

    public String getNumberOfBitsPerVariable()
    {
        return numberOfBitsPerVariable;
    }

    public void setNumberOfBitsPerVariable(String numberOfBitsPerVariable)
    {
        this.numberOfBitsPerVariable = numberOfBitsPerVariable;
    }
}
