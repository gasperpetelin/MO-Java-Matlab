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
    public void openSession()
    {
        try
        {
            if(this.proxy==null)
                this.proxy = factory.getProxy();
        }
        catch (MatlabConnectionException e)
        {
            throw new JMetalException("Failed to open session", e);
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
    public void newObject(String objectName, String parameters)
    {
        String command = this.config.getVariableName() + "=" + objectName + "(" + parameters +");";
        this.executeCommand(command);
    }

    @Override
    public void setPath(String path)
    {
        this.executeCommand("cd " + path);
    }

    @Override
    public int getNumberOfObjectives()
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getNumberOfObjectives();
            Object var = this.proxy.getVariable(command);
            return (int) ((double[])var)[0];
        }
        catch (MatlabInvocationException e)
        {
            throw new JMetalException("Unable to get number of objectives", e);
        }
    }

    @Override
    public int getNumberOfVriables()
    {
        try
        {
            String command = this.config.getVariableName()+"."+this.config.getNumberOfVariables();
            Object var = this.proxy.getVariable(command);
            return (int) ((double[])var)[0];
        }
        catch (MatlabInvocationException e)
        {
            throw new JMetalException("Unable to get number of variables", e);
        }
    }

    protected void executeCommand(String command)
    {
        try
        {
            proxy.eval(command);
        }
        catch (MatlabInvocationException e)
        {
            throw new JMetalException("Failed to execute command", e);
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
