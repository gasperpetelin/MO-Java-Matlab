package ExternalProblems.ProblemBuilders.Abstractions;


import CommunicationManager.CommandManager;
import CommunicationManager.ICommandManager;
import CommunicationManager.Implementations.Limit;

import java.util.ArrayList;
import java.util.List;

public abstract class MatlabAbstractDoubleProblemBuilder<T extends MatlabAbstractDoubleProblemBuilder<?>>
        extends MatlabAbstractProblemBuilder<T>
{

    protected List<Double> lowerLimitConstraint = new ArrayList();
    protected List<Double> upperLimitConstraint = new ArrayList();
    protected boolean BoundsSetProgrammatically = false;

    protected List<Double> addDefaultBound(int numberofVariables, double limit)
    {
        List<Double> lowerLimitCo = new ArrayList();
        for (int i = 0; i < numberofVariables; ++i)
        {
            lowerLimitCo.add(limit);
        }
        return lowerLimitCo;
    }

    protected void LimitsCheck(int numberofVariables)
    {
        if(!this.BoundsSetProgrammatically)
        {
            Limit[] lims= manager.getLimits(numberofVariables);
            if(lims==null)
            {
                this.handleMissingLimits(numberofVariables);
            }
            else
            {
                for(Limit l : lims)
                {
                    this.lowerLimitConstraint.add(l.getLower());
                    this.upperLimitConstraint.add(l.getUpper());
                    this.BoundsSetProgrammatically = true;
                }
            }
        }
        else
        {
            if(lowerLimitConstraint.size() != numberofVariables)
            {
                this.handleMissingLimits(numberofVariables);
            }
        }
    }


    protected void handleMissingLimits(int numberofVariables)
    {
        System.err.println("Number of limits should be same as number of variables. limits (-100, 100) will be used");
        lowerLimitConstraint = this.addDefaultBound(numberofVariables, -100.0);
        upperLimitConstraint = this.addDefaultBound(numberofVariables, +100.0);
    }

    public MatlabAbstractDoubleProblemBuilder(String problemName, ICommandManager manager)
    {
        super(problemName, manager);
    }
}
