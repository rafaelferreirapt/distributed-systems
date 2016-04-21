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
        communicate(new Message(MessageType.startTrial));
    }

    @Override
    public void assertTrialDecision() {
        communicate(new Message(MessageType.assertTrialDecision));
    }

    @Override
    public void pullTheRope(int id, String team) {
        communicate(new Message(MessageType.pullTheRope, team, id));
    }

    @Override
    public void waitForStartTrial() {
        communicate(new Message(MessageType.waitForStartTrial));
    }

    @Override
    public void waitForAssertTrialDecision() {
        communicate(new Message(MessageType.waitForAssertTrialDecision));
    }
}
