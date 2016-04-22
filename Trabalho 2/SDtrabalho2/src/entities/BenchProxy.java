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
import settings.NodeSettsProxy;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class BenchProxy implements IReferee, ICoach, IContestant{
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    public BenchProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("bench");
        SERVER_PORT = proxy.SERVER_PORTS().get("bench");
    }
    
    private void communicate(Message m){
        ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void callTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void wakeUp() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void reviewNotes(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    @Override
    public void callContestants(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    @Override
    public void waitForCallTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void waitForAssertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void waitForFollowCoachAdvice(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    @Override
    public void followCoachAdvice(String team, int idC) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, idC));
    }

    @Override
    public void seatDown(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    @Override
    public void waitForCallContestants(String team, int idC) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, idC));
    }
}
