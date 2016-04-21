package communication.proxy;

import communication.ClientChannel;
import communication.message.Message;
import communication.message.WrapperMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ClientProxy extends Thread {
    
    private final String clientProxyServerName;
    private final int toServerPort;
    private final Message outMessage;
    private final WrapperMessage result;
    
    public ClientProxy(String clientProxyServerName, int toServerPort, WrapperMessage result,
            Message outMessage){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.outMessage = outMessage;
        this.result = result;
    }
    
    @Override
    public void run(){
        try {
            ClientChannel con = new ClientChannel(this.clientProxyServerName, this.toServerPort);
            
            while (!con.open())
            {
                try {
                    sleep((long) (10));
                } catch (InterruptedException e) {
                }
            }   
            
            con.writeObject(outMessage);
            
            this.result.setMessage((Message) con.readObject());
            
            con.close();
            
        } catch (Exception ex) {
            Logger.getLogger(ClientProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
