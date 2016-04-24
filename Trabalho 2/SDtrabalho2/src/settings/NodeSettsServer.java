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
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSettsServer extends NodeSetts implements ServerInterface {
    
    private boolean serverEnded;
    
    public NodeSettsServer() {
        super();
        this.serverEnded = false;
    }
    
    
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case SERVER_PORTS:
                return new Message(MessageType.ACK, NodeSetts.SERVER_PORTS);
            case SERVER_HOSTS:
                return new Message(MessageType.ACK, NodeSetts.SERVER_HOSTS);
            case N_COACHS:
                return new Message(MessageType.ACK, NodeSetts.N_COACHS);
            case NUMBER_OF_TRIALS:
                return new Message(MessageType.ACK, NodeSetts.NUMBER_OF_TRIALS);
            case NUMBER_OF_GAMES:
                return new Message(MessageType.ACK, NodeSetts.NUMBER_OF_GAMES);
            case N_CONTESTANTS_TEAM:
                return new Message(MessageType.ACK, NodeSetts.N_CONTESTANTS_TEAM);
            case MAX_STRENGTH:
                return new Message(MessageType.ACK, NodeSetts.MAX_STRENGTH);
            case MIN_STRENGTH:
                return new Message(MessageType.ACK, NodeSetts.MIN_STRENGTH);
            case teams:
                return new Message(MessageType.ACK, NodeSetts.teams);
            case DELAY_MIN:
                return new Message(MessageType.ACK, NodeSetts.DELAY_MIN);
            case DELAY_MAX:
                return new Message(MessageType.ACK, NodeSetts.DELAY_MAX);
        }
        
        return new Message(MessageType.ACK);   
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
}
