# Distributed Systems Project #2

This project implements a multithreaded key-value store using RPC (Java RMI). The project includes the following components:

1. `RemoteCommandHandler` -  RMI interface for remote commands.
2. `RemoteCommandHandlerImpl` - Implementation of the RMI interface.
3. `RMIServer` - RMI server implementation.
4. `RMIClient` - RMI client implementation.

## Prerequisites

Ensure that you have the Java Development Kit (JDK) installed on your system. You can download it from [here](https://www.oracle.com/java/technologies/javase-downloads.html).

## Compilation
To compile the Java files, open a terminal or command prompt and navigate to the directory containing the source files. Use the following commands to compile each file:

```
javac RemoteCommandHandler.java RemoteCommandHandlerImpl.java RMIServer.java RMIClient.java
```

## Running the Server
To start the RMI server, use the following commands. Replace `<port>` with the port number you wish to use (e.g., 32000).

```
java RMIServer <port>
```

## Running the Client
To start the RMI client, use the following commands. Replace `<hostname>` with the server's hostname or IP address (e.g., localhost), and `<port>` with the same port number used for the server.

``` 
java RMIClient <hostname> <port>
```

## Example Usage
Here is an example of how to run the servers and clients using port `32000` and localhost as the hostname.

### Starting the RMI Server
```
java RMIServer 32000
```

### Running the RMI Client
```
java RMIClient localhost 32000
```

>Note: Ensure that the server is running first and use separate terminals or consoles for each client/server.