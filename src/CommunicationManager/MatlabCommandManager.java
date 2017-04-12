package CommunicationManager;


import MatlabVariableTransformations.AbstractMatlabVariables;
import MatlabVariableTransformations.Implementations.DoubleFunctionArgument;
import matlabcontrol.*;
import matlabcontrol.extensions.MatlabTypeConverter;
import org.uma.jmetal.util.JMetalException;

public class MatlabCommandManager implements ICommandManager
{

    public static ICommandManager newInstance()
    {
        try
        {
            return new MatlabCommandManager();
        }
        catch (MatlabConnectionException ex)
        {
            throw new JMetalException("Failed Command Manager creation");
        }

    }

    MatlabProxyFactoryOptions.Builder builder;
    MatlabProxyFactory factory;
    MatlabProxy proxy;
    private MatlabCommandManager() throws MatlabConnectionException
    {
        builder = new MatlabProxyFactoryOptions.Builder();
        builder.setUsePreviouslyControlledSession(true);
        factory = new MatlabProxyFactory(builder.build());
    }


    @Override
    public void openSession() throws MatlabConnectionException
    {
        if(proxy==null)
            proxy = factory.getProxy();

    }

    @Override
    public void closeSession()
    {
        proxy.disconnect();
        proxy = null;
    }

    @Override
    public void executeCommand(String command) throws MatlabInvocationException
    {
        proxy.eval(command);
    }

    @Override
    public boolean isSessionOpen()
    {
        if(proxy==null)
            return false;
        return proxy.isConnected();
    }

    @Override
    public void newObject(String variable, String objectName, String parameters) throws MatlabInvocationException
    {
        String command = variable + "=" + objectName + "(" + parameters +");";
        this.executeCommand(command);
    }

    @Override
    public void setVariable(String name, AbstractMatlabVariables value) throws MatlabInvocationException
    {
        this.executeCommand(name + " = " + value.toMatlabStructure());
    }

    @Override
    public <T extends AbstractMatlabVariables> T getVariable(String name, Class<T> newClass) throws MatlabInvocationException, IllegalAccessException, InstantiationException, MatlabConnectionException
    {
        Object a = this.proxy.getVariable(name);
        T type = newClass.newInstance();
        type.setMatlabValue(a);
        return type;
    }

    @Override
    public void setProblemPath(String path) throws MatlabInvocationException
    {
        this.executeCommand("cd " + path);
    }

    @Override
    public Object getVariable(String name)
    {
        try
        {
            return this.proxy.getVariable(name);
        } catch (MatlabInvocationException e)
        {
            return null;
        }
    }

    public double[][] get2DArray(String name)
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

    @Override
    public ConstraintEvaluation getConstraint(String objectName, String functionName, String arguments)
    {
        try
        {
            String command = objectName + "." + functionName + "(" + arguments + ")";
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


}
