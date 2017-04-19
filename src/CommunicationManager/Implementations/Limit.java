package CommunicationManager.Implementations;

public class Limit
{
    public double getLower()
    {
        return l;
    }

    public double getUpper()
    {
        return u;
    }

    private double l, u;
    public Limit(double lowerLimit, double upperLimit)
    {
        l = lowerLimit; u = upperLimit;
    }
}
