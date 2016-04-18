/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bench;

import communication.ServerChannel;
import communication.message.Message;
import communication.message.MessageException;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean serviceEnded() {
        return serverEnded;
    }
    
    
}
