package communication.proxy;

import communication.ClientChannel;
import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ClientProxy extends Thread {
    
    private final String clientProxyServerName;
    private final int toServerPort;
    private final MessageType mt;
    private final WrapperMessage result;
    private String team = null;
    private int idC = -1;
    
    public ClientProxy(String clientProxyServerName, int toServerPort, WrapperMessage result,
            MessageType mt){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.mt = mt;
        this.result = result;
    }
    
    public ClientProxy(String clientProxyServerName, int toServerPort, WrapperMessage result,
            MessageType mt, String team){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.mt = mt;
        this.result = result;
        this.team = team;
    }
    
    public ClientProxy(String clientProxyServerName, int toServerPort, WrapperMessage result,
            MessageType mt, String team, int idC){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.mt = mt;
        this.result = result;
        this.team = team;
        this.idC = idC;
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
            
            Message outMessage;
            
            if(this.team != null && this.idC != -1){
                outMessage = new Message(mt, team, idC);
            }else if(this.team != null && this.idC == -1){
                outMessage = new Message(mt, team);
            }else{
                outMessage = new Message(mt);
            }
            con.writeObject(outMessage);
            
            this.result.setMessage((Message) con.readObject());
            
            con.close();
            
        } catch (Exception ex) {
            Logger.getLogger(ClientProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
