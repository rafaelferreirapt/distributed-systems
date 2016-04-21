/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.proxy.ClientProxyWrapper;
import playground.IContestant;
import playground.IReferee;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class PlaygroundProxy implements IReferee, IContestant{
    
    private final String SERVER_HOST = NodeSetts.SERVER_HOSTS.get("playground");
    private final int SERVER_PORT = NodeSetts.SERVER_PORTS.get("playground");
    
    private void communicate(Message m){
        ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void startTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void pullTheRope(int id, String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, id));
    }

    @Override
    public void waitForStartTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void waitForAssertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
}
