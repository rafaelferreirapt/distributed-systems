/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bench;

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
public class BenchServer extends Bench implements ServerInterface {
    
    private boolean serverEnded;
    
    public BenchServer(int nContestantsTeamA, int nContestantsTeamB) {
        super(nContestantsTeamA, nContestantsTeamB);
        this.serverEnded = false;
    }
    
    
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        
        switch(inMessage.getType()){
            case TERMINATE:
                this.serverEnded = true;
            case callTrial:
                super.callTrial();
                break;
            case assertTrialDecision:
                super.assertTrialDecision();
                break;
            case wakeUp:
                super.wakeUp();
                break;
            case reviewNotes:
                super.reviewNotes(inMessage.getString());
                break;
            case callContestants:
                super.callContestants(inMessage.getString());
                break;
            case waitForCallTrial:
                super.waitForCallTrial();
                break;
            case waitForAssertTrialDecision:
                super.waitForAssertTrialDecision();
                break;
            case waitForFollowCoachAdvice:
                super.waitForFollowCoachAdvice(inMessage.getString());
                break;
            case followCoachAdvice:
                super.followCoachAdvice(inMessage.getString(), inMessage.getInteger());
                break;
            case seatDown:
                super.seatDown(inMessage.getString());
                break;
            case waitForCallContestants:
                super.waitForCallContestants(inMessage.getString(), inMessage.getInteger());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
}
