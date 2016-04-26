/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
import communication.message.MessageType;
import communication.proxy.ServerInterface;
import java.net.SocketException;

/**
 * Node settings server
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSettsServer extends NodeSetts implements ServerInterface {
    
    private boolean serverEnded;
    
    /**
     * Node settings server
     * @param jsonfilepath
     */
    public NodeSettsServer(String jsonfilepath) {
        super(jsonfilepath);
        this.serverEnded = false;
    }
    
    /**
    * Process and reply all the messages
     * @throws communication.message.MessageException
     * @throws java.net.SocketException
    */
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case SERVER_PORTS:
                return new Message(MessageType.ACK, super.SERVER_PORTS);
            case SERVER_HOSTS:
                return new Message(MessageType.ACK, super.SERVER_HOSTS);
            case N_COACHS:
                return new Message(MessageType.ACK, super.N_COACHS);
            case NUMBER_OF_TRIALS:
                return new Message(MessageType.ACK, super.NUMBER_OF_TRIALS);
            case NUMBER_OF_GAMES:
                return new Message(MessageType.ACK, super.NUMBER_OF_GAMES);
            case N_CONTESTANTS_TEAM:
                return new Message(MessageType.ACK, super.N_CONTESTANTS_TEAM);
            case MAX_STRENGTH:
                return new Message(MessageType.ACK, super.MAX_STRENGTH);
            case MIN_STRENGTH:
                return new Message(MessageType.ACK, super.MIN_STRENGTH);
            case teams:
                return new Message(MessageType.ACK, super.teams);
            case DELAY_MIN:
                return new Message(MessageType.ACK, super.DELAY_MIN);
            case DELAY_MAX:
                return new Message(MessageType.ACK, super.DELAY_MAX);
        }
        
        return new Message(MessageType.ACK);   
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
