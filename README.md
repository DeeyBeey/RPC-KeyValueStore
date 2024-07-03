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

## Executive Summmary
### Assignment Overview
Very similar to the previous assignment, we switch gears and change the means of communication from TCP and UDP to RPC, and in our case since we are using Java, specifically Java RMI. I believe this project also simulates a distributed system, but on a smaller scale, as this is how real-world systems would communicate with each other by invoking methods on different systems to perform a particular action. This also dives a little deeper into the complexities of distributed systems since we get an opportunity to take a look at threads and pooling of threads, along with the handling of mutual exclusion since multiple resources are shared concurrently. This is in contrast to the previous assignment, wherein the operations that the clients were performing were not concurrent and it was a single-threaded key value store. Although we were given an opportunity to use external libraries to implement RPC like Apache Thrift, I took the opportunity to use Java and deepen my understanding of RPC using Java's RMI package. In terms of practical application, we can see RMI being used where distributed objects interact over a network. The client-server architecture is simplified because the remote calls seem like local calls, which hides the underlying complexities and makes this design very intuitive. 

### Technical Impression
The most striking thing about this assignment was the importance of having a modular structure for the source code. Before the first project, there was emphasis on code reuse as the second project was supposed to be in continuation, and I was quickly able to take advantage of this opportunity as I ended up saving lots of time as most of the code was reused from the first project. I spent significantly more time understanding how RMI works instead of refactoring the code, which I think is a major learning from this project. This also signifies the benefits of writing clean, modular code that is also well documented so that you can go back into it and edit it. There was just one thing that I was unclear about in this project. I did not know how well the specifications of the previous project must be followed, but eventually I figured that since this is a continuation, all features must be implemented.

Finally, I was able to see how RPC in Java using RMI provides higher abstraction and reduces the number of lines of code, as most of the work is done automatically by the packages, whereas socket programming with TCP and UDP was more on the manual side of things that a programmer had to explicitly do and had significantly more lines of code. One very noticeable difference was taking care of the data at the packet level; we did not have to worry about that with RPC. Apart from that, the RMI package has much better error handling when we compare the errors that could arise from the transmission of packets.