/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.proxy;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import entities.LogProxy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ClientProxyWrapper {
    
    public static WrapperMessage connect(String logServerName, int logServerPort, Message m){
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(logServerName, logServerPort, result, m);
        
        cp.start();
        
        try {
            // System.out.printf("[%s][%d][%s] Init Join\n", logServerName, logServerPort, m.getType().toString()); 
            cp.join(); 
            // System.out.printf("[%s][%d][%s] Init Join\n", logServerName, logServerPort, m.getType().toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(LogProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
        
        return result;
    }
}
