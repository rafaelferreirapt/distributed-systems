/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ServerChannel {
    private ServerSocket listenSocket = null;
    private Socket commSocket = null;
    private final int serverPort;
    private ObjectInputStream request = null;
    private ObjectOutputStream response = null;
    
    /**
     * Method for read object
     * @param serverPort
     */
    public ServerChannel(int serverPort) {
        this.serverPort = serverPort;
    }
    
    /**
     * Construct for create server channel with server port and server socket
     * @param serverPort
     * @param lSocket
     */
    public ServerChannel(int serverPort, ServerSocket lSocket) {
        this(serverPort);
        this.listenSocket = lSocket;
    }
    
    /**
     * Start the server channel
     */
    public void start() {
        try {
            this.listenSocket = new ServerSocket(this.serverPort);
        } catch (BindException e){
            System.out.println(Thread.currentThread().getName() + " port not available: " + this.serverPort + "!\n Error:" + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " undefined error port: " + this.serverPort + "!\n Error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * End the server channel
     */
    public void end() {
        try {
            this.listenSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " is not possible to close the socket");
            System.exit(1);
        }
    }
    
    /**
     * Accept connection
     * @return 
     * @throws java.net.SocketTimeoutException
     */
    public ServerChannel accept() throws SocketTimeoutException {
        ServerChannel server_channel;
        
        server_channel = new ServerChannel(this.serverPort, this.listenSocket);
        
        try {
            server_channel.commSocket = this.listenSocket.accept();
        } catch (SocketException e) {
            System.out.println(Thread.currentThread().getName() + ", the socket has been closed during the listening");
            System.exit(1);
        } catch(SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open a communication channel for a pending request");
            System.exit(1);
        }
        
        try {
            server_channel.request = new ObjectInputStream(server_channel.commSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open the socket input channel!");
            System.exit(1);
        }

        try {
            server_channel.response = new ObjectOutputStream(server_channel.commSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", could not open the socket output channel!");
            System.exit(1);
        }

        return server_channel;
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            request.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the socket input channel!");
            System.exit(1);
        }

        try {
            response.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the output channel socket!");
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", unable to close the communication socket!");
            System.exit(1);
        }
    }

    /**
     * Read object
     * @return 
     */
    public Object readObject() {
        Object from_client = null; 
        
        try {
            from_client = request.readObject();
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName() + ", the readed object is not capable of deserializer!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", error in the reading of a communication socket input channel object");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println(Thread.currentThread().getName() + ", read the object corresponds to an unknown type of data");
            System.exit(1);
        }

        return from_client;
    }

    /**
     * Write connection
     * @param toClient
     */
    public void writeObject(Object toClient) {
        try {
            response.writeObject(toClient);
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName() + ", the object to be written is not capable of serialization!");
            System.exit(1);
        } catch (NotSerializableException e) {
            System.out.println(Thread.currentThread().getName() + ", the object to be written belongs to a type of non serializable data!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + ", error in the writing of an output channel of the object of the communication socket!");
            System.exit(1);
        }
    }
}
