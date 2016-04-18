/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.proxy;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ServerProxy extends Thread {
    
    private static int nProxy;

    private final ServerChannel sconi;

    private final ServerInterface sInterface;
    
    private final ServerChannel scon;

    
    public ServerProxy(ServerChannel scon, ServerChannel sconi, ServerInterface sInterface) {
        super("Proxy_" + getProxyId());

        this.sconi = sconi;
        this.sInterface = sInterface;
        this.scon = scon;
    }

    @Override
    public void run() {
        Message request = null;  // mensagem de entrada
        Message response = null; // mensagem de saída

        request = (Message) sconi.readObject();
        
        try{
            response = sInterface.processAndReply(request, scon);
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageObject().toString());
            System.exit(1);
        } catch (SocketException ex) {
            Logger.getLogger(ServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sconi.writeObject(response);                                // enviar resposta ao cliente
        sconi.close();                                                // fechar canal de comunicação
        
        if(sInterface.serviceEnded())
        {
            System.out.println("Closing service ... Done!");
            System.exit(0);
        }
    }

    public static int getProxyId(){
        return nProxy;
    }
}
