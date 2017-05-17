package ConnectionManager;

import ConnectionManager.ManagerConfigs.MatlabManagerConfig;
import matlabcontrol.*;
import matlabcontrol.extensions.MatlabTypeConverter;
import org.uma.jmetal.util.JMetalException;

public abstract class MatlabManager<C extends MatlabManagerConfig> implements IManager
{
    protected C config;

    protected MatlabProxyFactoryOptions.Builder builder;
    protected MatlabProxyFactory factory;
    protected MatlabProxy proxy;

    public MatlabManager(C config)
    {
        this.config = config;
        builder = new MatlabProxyFactoryOptions.Builder();
        builder.setUsePreviouslyControlledSession(true);
        factory = new MatlabProxyFactory(builder.build());
    }


    @Override
    public void openSession() throws CommandExecutionException
    {
        try
        {
            if(this.proxy==null)
                this.proxy = factory.getProxy();
        }
        catch (MatlabConnectionException e)
        {
            throw new CommandExecutionException("Failed to open session", e);
        }
    }

    @Override
    public void closeSession()
    {
        if(proxy!=null)
            proxy.disconnect();
        proxy = null;
    }

    @Override
    public void newObject(String objectName, String parameters) throws CommandExecutionException
    {
        String command = this.config.getVariableName() + "=" + objectName + "(" + parameters +");";
        this.executeCommand(command);
    }

    @Override
    public void setPath(String path) throws CommandExecutionException
    {
        try
        {
            this.executeCommand("cd " + path);
        }
        catch(CommandExecutionException e)
        {
            throw new CommandExecutionException("Failed to move to directory: " + path, e);
        }
    }

    @Override
    public int getNumberOfObjectives() throws CommandExecutionException
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getNumberOfObjectives();
            Object var = this.proxy.getVariable(command);
            return (int) ((double[])var)[0];
        }
        catch (MatlabInvocationException e)
        {
            throw new CommandExecutionException("Unable to get number of objectives", e);
        }
    }

    @Override
    public int getNumberOfVariables() throws CommandExecutionException
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getNumberOfVariables();
            Object var = this.proxy.getVariable(command);
            return (int) ((double[])var)[0];
        }
        catch (MatlabInvocationException e)
        {
            throw new CommandExecutionException("Unable to get number of variables", e);
        }
    }

    @Override
    public String getProblemName() throws CommandExecutionException
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getProblemName();
            return this.proxy.getVariable(command).toString();

        }
        catch (MatlabInvocationException e)
        {
            throw new CommandExecutionException("Unable to get number of variables", e);
        }
    }

    protected void executeCommand(String command) throws CommandExecutionException
    {
        try
        {
            proxy.eval(command);
        }
        catch (MatlabInvocationException e)
        {
            throw new CommandExecutionException("Failed to execute command", e);
        }
    }

    public double[][] get2DArray(String name)
    {
        MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
        try
        {
            return processor.getNumericArray(name).getRealArray2D();
        }
        catch (MatlabInvocationException e)
        {
            return null;
        }
    }
}
