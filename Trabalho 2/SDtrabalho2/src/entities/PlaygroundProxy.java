/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.proxy.ClientProxy;
import playground.IContestant;
import playground.IReferee;
import settings.NodeSettsProxy;

/**
 * Playground Proxy
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class PlaygroundProxy implements IReferee, IContestant{
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Playground Proxy
    */
    public PlaygroundProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Playground");
        SERVER_PORT = proxy.SERVER_PORTS().get("Playground");
    }
    
    /**
    * Communicate method to communicate with the Playground
    */
    private void communicate(Message m){
        ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion".
     */
    @Override
    public void startTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion".
     */
    @Override
    public void assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * Random time interval in the simulation 
     * @param id contestant identifier
     * @param team "A" or "B"
     */
    @Override
    public void pullTheRope(int id, String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, id));
    }
    
    /**
     * Wait for start trial. Contestant method.
     */
    @Override
    public void waitForStartTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * Wait for assert trial decision. Contestant method.
     */
    @Override
    public void waitForAssertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
}
