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
    
    private final boolean serverEnded;
    
    public BenchServer(int nContestantsTeamA, int nContestantsTeamB) {
        super(nContestantsTeamA, nContestantsTeamB);
        this.serverEnded = false;
    }
    
    
    @Override
    public Message processAndReply(Message inMessage, ServerChannel scon) throws MessageException, SocketException {
        
        switch(inMessage.getType()){
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
                super.reviewNotes(inMessage.getTeam());
                break;
            case callContestants:
                super.callContestants(inMessage.getTeam());
                break;
            case waitForCallTrial:
                super.waitForCallTrial();
                break;
            case waitForAssertTrialDecision:
                super.waitForAssertTrialDecision();
                break;
            case waitForFollowCoachAdvice:
                super.waitForFollowCoachAdvice(inMessage.getTeam());
                break;
            case followCoachAdvice:
                super.followCoachAdvice(inMessage.getTeam(), inMessage.getIdC());
                break;
            case seatDown:
                super.seatDown(inMessage.getTeam());
                break;
            case waitForCallContestants:
                super.waitForCallContestants(inMessage.getTeam(), inMessage.getIdC());
                break;
        }
        
        return new Message(MessageType.ACK);
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
}
