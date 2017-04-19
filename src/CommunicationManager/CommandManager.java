package CommunicationManager;


import CommunicationManager.Implementations.ConstraintEvaluation;
import CommunicationManager.Implementations.Limit;
import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.IntFunctionArgument;
import MatlabVariableTransformations.Implementations.StringFunctionArgument;
import matlabcontrol.*;
import matlabcontrol.extensions.MatlabTypeConverter;
import org.uma.jmetal.util.JMetalException;

import javax.management.JMException;

public abstract class CommandManager implements ICommandManager
{
    public void setEvaluateFunctionName(String evaluateFunctionName)
    {
        this.evaluateFunctionName = evaluateFunctionName;
    }

    public void setEvaluateConstraintsFunctionName(String evaluateConstraintsFunctionName)
    {
        this.evaluateConstraintsFunctionName = evaluateConstraintsFunctionName;
    }

    @Override
    public void setProblemNameFieldName(String name)
    {
        this.problemNameDefaultField = name;
    }


    protected String evaluateFunctionName = "evaluate";
    protected String evaluateConstraintsFunctionName = "evaluateConstraints";

    protected String nameOfObjectVariable = "createdVariableName";

    private String numberOfVariablesFieldName = "NumberOfVariables";
    private String numberOfObjectivesFieldName = "NumberOfObjectives";

    private String problemNameDefaultField = "Name";


    protected MatlabProxyFactoryOptions.Builder builder;
    protected MatlabProxyFactory factory;
    protected MatlabProxy proxy;

    public CommandManager()
    {
        builder = new MatlabProxyFactoryOptions.Builder();
        builder.setUsePreviouslyControlledSession(true);
        factory = new MatlabProxyFactory(builder.build());
    }

    public void openSession()
    {
        try
        {
            if (proxy == null)
                proxy = factory.getProxy();
        }
        catch (MatlabConnectionException ex)
        {
            throw new JMetalException("Connection failed to open", ex);
        }

    }

    public void closeSession()
    {
        proxy.disconnect();
        proxy = null;
    }

    private void executeCommand(String command) throws MatlabInvocationException
    {
        proxy.eval(command);
    }

    public boolean isSessionOpen()
    {
        if(proxy==null)
            return false;
        return proxy.isConnected();
    }

    public void newObject(String objectName, String parameters) throws MatlabInvocationException
    {
        String command = this.nameOfObjectVariable + "=" + objectName + "(" + parameters +");";
        this.executeCommand(command);
    }

    private double[][] get2DArray(String name)
    {
        MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
        try
        {
            return processor.getNumericArray(name).getRealArray2D();
        } catch (MatlabInvocationException e)
        {
            return null;
        }
    }



    public void setProblemPath(String path) throws MatlabInvocationException
    {
        this.executeCommand("cd " + path);
    }

    public ConstraintEvaluation getConstraint(String arguments)
    {
        try
        {
            String command = this.nameOfObjectVariable + "." + this.evaluateConstraintsFunctionName + "(" + arguments + ")";
            Object[] r = proxy.returningEval(command, 2);
            double d1 =((double[])r[0])[0];
            double d2 =((double[])r[1])[0];
            return new ConstraintEvaluation(d1, ((Double)d2).intValue());
        }
        catch (Exception e)
        {
            throw new JMetalException("Error evaluating constraint", e);
        }
    }

    public <T extends AbstractMatlabVariables> T getVariable(String name, Class<T> newClass) throws MatlabInvocationException, IllegalAccessException, InstantiationException, MatlabConnectionException
    {
        Object a = this.proxy.getVariable(name);
        T type = newClass.newInstance();
        type.setMatlabValue(a);
        return type;
    }

    public double[] getSolution(String objectName, String arguments, int numberOfObjectives)
    {
        try
        {
            String com = this.nameOfObjectVariable + "." + this.evaluateFunctionName + "(" + arguments + ")";
            Object[] r = proxy.returningEval(com, 1);
            double[] obj = new double[numberOfObjectives];
            for (int i = 0; i < numberOfObjectives; i++)
            {
                obj[i] = ((double[])r[0])[i];
            }
            return obj;
        }
        catch (Exception e)
        {
            throw new JMetalException("Error evaluating fitness function", e);
        }
    }

    @Override
    public void setObjectName(String name)
    {
        this.nameOfObjectVariable = name;
    }


    @Override
    public int getNumberOfVariables()
    {
        try
        {
            return this.getVariable(this.nameOfObjectVariable  + "." + this.numberOfVariablesFieldName, IntFunctionArgument.class).getValue();
        }
        catch (Exception e)
        {
            throw new JMetalException("Unable to get number of variables", e);
        }

    }

    @Override
    public int getNumberOfObjectives()
    {
        try
        {
            return this.getVariable(this.nameOfObjectVariable + "." + numberOfObjectivesFieldName, IntFunctionArgument.class).getValue();
        }
        catch (Exception e)
        {
            throw new JMetalException("Unable to get number of objectives", e);
        }
    }

    @Override
    public String getProblemName()
    {
        try
        {
            return this.getVariable(this.nameOfObjectVariable  + "." + this.problemNameDefaultField, StringFunctionArgument.class).getValue();
        }
        catch (Exception e)
        {
            throw new JMetalException("Unable to get number of variables", e);
        }
    }

    @Override
    public void setNumberOfVariablesFieldName(String name)
    {
        this.numberOfVariablesFieldName = name;
    }

    @Override
    public void setNumberOfObjectivesFieldName(String name)
    {
        this.numberOfObjectivesFieldName = name;
    }

    @Override
    public Limit[] getLimits(int numberOfVariables)
    {
        Limit[] lims = new Limit[numberOfVariables];

        double[][] limits = this.get2DArray(nameOfObjectVariable + ".Limits");
        if (limits==null || limits.length != numberOfVariables)
        {
            return null;
        }
        else
        {
            for (int i = 0; i < numberOfVariables; ++i)
            {
                lims[i] = new Limit(limits[i][0], limits[i][1]);
            }
        }
        return lims;
    }
}
