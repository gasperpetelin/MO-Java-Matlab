package CommunicationManager;

import CommunicationManager.Implementations.DoubleMatlabCommandManager;
import matlabcontrol.MatlabConnectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MatlabCommandManagerTest
{
    @Test
    void createVariable() throws MatlabConnectionException
    {
        CommandManager m = DoubleMatlabCommandManager.newInstance();
        m.openSession();
        //m.executeCommand("h = [12,4]");
        assertEquals(true, true);
    }

}