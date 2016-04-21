/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import bench.ICoach;
import bench.IContestant;
import bench.IReferee;
import communication.message.Message;
import communication.message.MessageType;
import communication.proxy.ClientProxyWrapper;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class BenchProxy implements IReferee, ICoach, IContestant{
    
    @Override
    public void callTrial() {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.callTrial));
    }

    @Override
    public void assertTrialDecision() {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.assertTrialDecision));
    }

    @Override
    public void wakeUp() {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.wakeUp));
    }

    @Override
    public void reviewNotes(String team) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.reviewNotes, team));
    }

    @Override
    public void callContestants(String team) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.callContestants, team));
    }

    @Override
    public void waitForCallTrial() {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.waitForCallTrial));
    }

    @Override
    public void waitForAssertTrialDecision() {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.waitForAssertTrialDecision));
    }

    @Override
    public void waitForFollowCoachAdvice(String team) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.waitForFollowCoachAdvice, team));
    }

    @Override
    public void followCoachAdvice(String team, int idC) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.followCoachAdvice, team, idC));
    }

    @Override
    public void seatDown(String team) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.seatDown, team));
    }

    @Override
    public void waitForCallContestants(String team, int idC) {
        ClientProxyWrapper.connect(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, new Message(MessageType.waitForCallContestants, team, idC));
    }
}
