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

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface ServerInterface {
    
    /**
     *
     * @param inMessage
     * @param scon
     * @return
     * @throws MessageException
     * @throws SocketException
     */
    public Message processAndReply (Message inMessage, ServerChannel scon) throws MessageException, SocketException;
    
    
    /**
     * Service end
     * @return 
     */
    public boolean serviceEnded();
}
