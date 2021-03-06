package ConnectionManager.ManagerInterfaces;

public class ConstraintEvaluation
{
    private double overallConstraintViolationDegree;
    private int numberOfViolatedConstraints;
    public ConstraintEvaluation(double overallConstraintViolationDegree, int numberOfViolatedConstraints)
    {
        this.overallConstraintViolationDegree = overallConstraintViolationDegree;
        this.numberOfViolatedConstraints = numberOfViolatedConstraints;
    }


    public double getOverallConstraintViolationDegree()
    {
        return overallConstraintViolationDegree;
    }

    public int getNumberOfViolatedConstraints()
    {
        return numberOfViolatedConstraints;
    }

    @Override
    public String toString()
    {
        return "[" + numberOfViolatedConstraints + ", " + overallConstraintViolationDegree + "]";
    }
}