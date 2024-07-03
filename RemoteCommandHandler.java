import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RemoteCommandHandler is a remote interface to handle commands.
 */

public interface RemoteCommandHandler extends Remote {

    /**
     * Handle the command with the arguments that are provided.
     * 
     * @param command The command to be executed (PUT, GET, DELETE).
     * @param args Arguments for the command.
     * @return A message displaying the result of the operation.
     * @throws RemoteException If an error occurs during remote communications.
     */

    String handleCommand(String command, String[] args) throws RemoteException;
}
