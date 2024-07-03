import java.util.HashMap;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RemoteCommandHandlerImpl class implements the RemoteCommandHandler interface.
 */
public class RemoteCommandHandlerImpl extends UnicastRemoteObject implements RemoteCommandHandler {

    /**
     * keyValueStore is the HashMap that stores the key-value pairs.
     */
    private final HashMap<String, String> keyValueStore;
    private final ExecutorService executor;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern
    ("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * A new RemoteCommandHandlerImpl is constructed with an empty keyValueStore and an ExecutorService.
     * @throws RemoteException if the remote object is not created.
     */
    protected RemoteCommandHandlerImpl(ExecutorService executor) throws RemoteException {
        super();
        this.keyValueStore = new HashMap<>();
        this.executor = executor;
    }

    /**
     * Handles the given command by choosing appropriate method.
     * 
     * @param command The command to be executed (PUT, GET, DELETE).
     * @param args Arguments for the command.
     * @return A message depicting the result of the operation.
     * @throws RemoteException if an error occurs during remote communication.
     */
    @Override
    public String handleCommand(String command, String[] args) throws RemoteException {
        Callable<String> task = () -> {
            logClientRequest(command, args);
            String result;
            switch (command) {
                case "PUT":
                    result = put(args);
                    break;
                case "GET":
                    result = get(args);
                    break;
                case "DELETE":
                    result = delete(args);
                    break;
                default:
                    result = "Invalid Command.";
            }
            logClientResponse(result);
            return result;
        };

        Future<String> future = executor.submit(task);

        try {
            return future.get(); // Get the result of the command execution
        } catch (Exception e) {
            throw new RemoteException("Error executing command", e);
        }
    }

    /**
     * Logs the client's request.
     * @param command The command received from the client.
     * @param args The arguments received with the command.
     */
    private void logClientRequest(String command, String[] args) {
        String argsString = String.join(" ", args);
        String message = "Received command: " + command + " " + argsString;
        printWithTimestamp(message);
    }

    /**
     * Logs the server's response to the client.
     * @param response The response sent to the client.
     */
    private void logClientResponse(String response) {
        String message = "Response: " + response;
        printWithTimestamp(message);
    }

    /**
     * Puts a key-value pair into the keyValueStore.
     * 
     * @param args Arguments containing the key and value to be inserted.
     * @return A message depicting success or failure of the operation.
     */
    private String put(String[] args) {
        if (args.length < 2) return "Sample Usage: PUT <key> <value>";
        String key = args[0];
        String value = args[1];
        keyValueStore.put(key, value);
        return "Operation successful.";
    }

    /**
     * Fetches a value for a given key from the keyValueStore.
     * 
     * @param args Argument containing the key for which the value must be retrieved.
     * @return Value for the associated key or an error message if key does not exist.
     */
    private String get(String[] args) {
        if (args.length < 1) return "Sample Usage: GET <key>";
        String key = args[0];
        String value = keyValueStore.get(key);
        return value != null ? value : "No record found.";
    }

    /**
     * Deletes a key-value pair record from the keyValueStore.
     * 
     * @param args Argument containing the key which must be deleted. 
     * @return A message depicting success or failure of the operation.
     */
    private String delete(String[] args) {
        if (args.length < 1) return "Sample Usage: DELETE <key>";
        String key = args[0];
        keyValueStore.remove(key);
        return "Operation successful.";
    }

    /**
     * Helper method to print the message with a timestamp.
     * @param message The message to be printed along with the timestamp on the console.
     */
    private static void printWithTimestamp(String message) {
        System.out.println("[" + LocalDateTime.now().format(formatter) + "] " + message);
    }
}
