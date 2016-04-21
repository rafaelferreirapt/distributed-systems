/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import communication.message.Message;
import general_info_repo.IPlayground;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxyWrapper;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class LogProxy implements IPlayground{

    private final String SERVER_HOST = NodeSetts.SERVER_HOSTS.get("log");
    private final int SERVER_PORT = NodeSetts.SERVER_PORTS.get("log");
    
    private WrapperMessage communicate(Message m){
        return ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void updateRope(String team, int contestant) {
        communicate(new Message(MessageType.updateRope, team, contestant));
    }
    
}