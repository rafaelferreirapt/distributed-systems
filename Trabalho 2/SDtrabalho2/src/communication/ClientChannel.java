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
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Class for all the client channels, this will have the hostName and serverPort,
 * as well will send the proxy information.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ClientChannel {
    
    private Socket commSocket = null;
    private final int serverPort;
    private final String hostName;
    private ObjectInputStream request = null;
    private ObjectOutputStream response = null;
    
    
    public ClientChannel(String hostName, int serverPort) {
        this.hostName = hostName;
        this.serverPort = serverPort;
    }
    
    public boolean open() {
        boolean success = true;
        SocketAddress serverAddress = new InetSocketAddress(this.hostName, serverPort);

        try {
            commSocket = new Socket();
            commSocket.connect(serverAddress);
        } catch (UnknownHostException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o nome do sistema computacional onde reside o servidor é desconhecido: "
                    + this.hostName + "!");
            System.exit(1);
        } catch (NoRouteToHostException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o nome do sistema computacional onde reside o servidor é inatingível: "
                    + this.hostName + "!");
            System.exit(1);
        } catch (ConnectException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o servidor não responde em: " + this.hostName + "." + serverPort + "!");
            if (e.getMessage().equals("Connection refused")) {
                success = false;
            } else {
                System.out.println(e.getMessage() + "!");
                System.exit(1);
            }
        } catch (SocketTimeoutException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - ocorreu um time out no estabelecimento da ligação a: "
                    + this.hostName + "." + serverPort + "!");
            success = false;
        } catch (IOException e) // erro fatal --- outras causas
        {
            System.out.println(Thread.currentThread().getName()
                    + " - ocorreu um erro indeterminado no estabelecimento da ligação a: "
                    + this.hostName + "." + serverPort + "!");
            System.exit(1);
        }

        if (!success) {
            return (success);
        }

        try {
            response = new ObjectOutputStream(commSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível abrir o canal de saída do socket!");
            System.exit(1);
        }

        try {
            request = new ObjectInputStream(commSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível abrir o canal de entrada do socket!");
            System.exit(1);
        }

        return (success);
    }
    
    public void close() {
        try {
            request.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o canal de entrada do socket!");
            System.exit(1);
        }

        try {
            response.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o canal de saída do socket!");
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o socket de comunicação!");
            System.exit(1);
        }
    }
    
    public Object readObject() {
        Object fromServer = null;
        
        try {
            fromServer = request.readObject();
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto lido não é passível de desserialização!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - erro na leitura de um objecto do canal de entrada do socket de comunicação!");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto lido corresponde a um tipo de dados desconhecido!");
            System.exit(1);
        }

        return fromServer;
    }
    
    public void writeObject(Object toServer) {
        try {
            response.writeObject(toServer);
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto a ser escrito não é passível de serialização!");
            System.exit(1);
        } catch (NotSerializableException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto a ser escrito pertence a um tipo de dados não serializável!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - erro na escrita de um objecto do canal de saída do socket de comunicação!");
            System.exit(1);
        }
    }
    
}
