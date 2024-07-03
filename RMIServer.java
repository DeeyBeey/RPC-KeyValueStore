import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * RMIServer class is a simple RMI Server listening to clients and handling requests 
 * using a CommandHandler.
 */
public class RMIServer {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Logger logger = Logger.getLogger(RMIServer.class.getName());
    private static final int THREAD_POOL_SIZE = 10;
    private static final Object loggerLock = new Object();

    /**
     * A Static initialization block to setup the logger for the RMI Server.
     */
    static {
        try {
            // Configure the logger with a file handler and a simple formatter
            FileHandler fileHandler = new FileHandler("rmiserver.log", true); // Use append mode
            fileHandler.setFormatter(new SimpleFormatter());
            
            synchronized (loggerLock) {
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
            }
        } catch (IOException e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
        }
    }

    /**
     * Main method for the RMI Server.
     * @param args Command Line Arguments to run the server: port number of the server.
     */
    public static void main(String[] args) {
        // Check for correct number of arguments to run the server
        if (args.length != 1) {
            printWithTimestamp("Sample Usage: java RMIServer <port number>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try {
            printWithTimestamp("RMI Server starting on port " + port);
            RemoteCommandHandlerImpl handler = new RemoteCommandHandlerImpl(executor);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("RemoteCommandHandler", handler);
            printWithTimestamp("RMI Server started and binding with RemoteCommandHandler confirmed.");

            // Keep the server running to handle incoming requests
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    printWithTimestamp("Shutting down RMI Server...");
                    executor.shutdown();
                    printWithTimestamp("RMI Server has shut down.");
                } catch (Exception e) {
                    printWithTimestamp("Error shutting down RMI Server: " + e.getMessage());
                    logError("Error shutting down RMI Server: " + e.getMessage());
                }
            }));

        } catch (IOException ex) {
            printWithTimestamp("RMI Server exception: " + ex.getMessage());
            logError("Server exception: " + ex.getMessage());
            executor.shutdown();
        }
    }

    /**
     * Helper method to print the message with a timestamp.
     * @param message The message to be printed along with the timestamp on the console.
     */
    private static void printWithTimestamp(String message) {
        System.out.println("[" + LocalDateTime.now().format(formatter) + "] " + message);
    }

    /**
     * Helper method to log an error message with a timestamp to avoid mutual exclusion.
     * @param message The error message to log.
     */
    private static void logError(String message) {
        synchronized (loggerLock) {
            logger.severe(message);
        }
    }
}
