/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import communication.message.Message;
import general_info_repo.IPlayground;
import communication.message.MessageType;
import communication.proxy.ClientProxyWrapper;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class LogProxy implements IPlayground{

    @Override
    public void updateRope(String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.updateRope, team, contestant));
    }
    
}