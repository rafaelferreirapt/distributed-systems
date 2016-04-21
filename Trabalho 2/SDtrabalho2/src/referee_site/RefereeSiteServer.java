/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package referee_site;

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
public class RefereeSiteServer extends RefereeSite implements ServerInterface {
    
    private final boolean serverEnded;
    
    public RefereeSiteServer() {
        super();
        this.serverEnded = false;
    }
    
    
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        boolean endMatch;
        switch(inMessage.getType()){
            case annouceNewGame:
                super.annouceNewGame();
                break;
            case declareGameWinner:
                super.declareGameWinner();
                break;
            case declareMatchWinner:
                super.declareMatchWinner();
                break;
            case endOfMatch:
                endMatch = super.endOfMatch();
                return new Message(MessageType.ACK, endMatch);   
            case waitForInformReferee:
                super.waitForInformReferee();
                break;
            case waitForAmDone:
                super.waitForAmDone();
                break;
            case waitAllPositioned:
                super.waitAllPositioned();
                break;
            case informReferee:
                super.informReferee(inMessage.getString());
                break;
            case amDone:
                super.amDone();
                break;
            case positioned:
                super.positioned();
                break;
        }
        
        return new Message(MessageType.ACK);   
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    
}
