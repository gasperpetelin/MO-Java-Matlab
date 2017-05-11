package ConnectionManager;

public class CommandExecutionException extends Exception
{
    public CommandExecutionException(String message)
    {
        super(message);
    }

    public CommandExecutionException(String message, Exception e)
    {
        super(message, e);
    }
}
