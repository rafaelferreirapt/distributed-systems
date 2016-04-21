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
    
    private final String SERVER_HOST = NodeSetts.SERVER_HOSTS.get("bench");
    private final int SERVER_PORT = NodeSetts.SERVER_PORTS.get("bench");
    
    private void communicate(Message m){
        ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void callTrial() {
        communicate(new Message(MessageType.callTrial));
    }

    @Override
    public void assertTrialDecision() {
        communicate(new Message(MessageType.assertTrialDecision));
    }

    @Override
    public void wakeUp() {
        communicate(new Message(MessageType.wakeUp));
    }

    @Override
    public void reviewNotes(String team) {
        communicate(new Message(MessageType.reviewNotes, team));
    }

    @Override
    public void callContestants(String team) {
        communicate(new Message(MessageType.callContestants, team));
    }

    @Override
    public void waitForCallTrial() {
        communicate(new Message(MessageType.waitForCallTrial));
    }

    @Override
    public void waitForAssertTrialDecision() {
        communicate(new Message(MessageType.waitForAssertTrialDecision));
    }

    @Override
    public void waitForFollowCoachAdvice(String team) {
        communicate(new Message(MessageType.waitForFollowCoachAdvice, team));
    }

    @Override
    public void followCoachAdvice(String team, int idC) {
        communicate(new Message(MessageType.followCoachAdvice, team, idC));
    }

    @Override
    public void seatDown(String team) {
        communicate(new Message(MessageType.seatDown, team));
    }

    @Override
    public void waitForCallContestants(String team, int idC) {
        communicate(new Message(MessageType.waitForCallContestants, team, idC));
    }
}
