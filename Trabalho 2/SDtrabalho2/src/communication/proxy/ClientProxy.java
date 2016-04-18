package communication.proxy;

import communication.ClientChannel;
import communication.message.Message;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ClientProxy extends Thread {
    
    private final String clientProxyServerName;
    private final int toServerPort;
    
    public ClientProxy(String clientProxyServerName, int toServerPort){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
    }
    
    
    @Override
    public void run(){
        ClientChannel con = new ClientChannel(this.clientProxyServerName, this.toServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        
        /*
        outMessage = new Message(MessageType.GO_SHOPPING, id);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        CustomerState cs = inMessage.getCustState();
        if (type != MessageType.ACK || cs == null) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        this.setState(cs);
        con.close();
        */
    }
    
}
