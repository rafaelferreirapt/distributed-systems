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
    
    @Override
    public void startTrial() {
        ClientProxyWrapper.connect(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, new Message(MessageType.startTrial));
    }

    @Override
    public void assertTrialDecision() {
        ClientProxyWrapper.connect(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, new Message(MessageType.assertTrialDecision));
    }

    @Override
    public void pullTheRope(int id, String team) {
        ClientProxyWrapper.connect(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, new Message(MessageType.pullTheRope, team, id));
    }

    @Override
    public void waitForStartTrial() {
        ClientProxyWrapper.connect(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, new Message(MessageType.waitForStartTrial));
    }

    @Override
    public void waitForAssertTrialDecision() {
        ClientProxyWrapper.connect(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, new Message(MessageType.waitForAssertTrialDecision));
    }
}
