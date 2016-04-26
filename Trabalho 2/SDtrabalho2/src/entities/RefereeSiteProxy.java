/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxy;
import referee_site.ICoach;
import referee_site.IContestant;
import referee_site.IReferee;
import settings.NodeSettsProxy;

/**
 * Referee site proxy
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSiteProxy implements IReferee, ICoach, IContestant{

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Referee Site Proxy
    */
    public RefereeSiteProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("RefereeSite");
        SERVER_PORT = proxy.SERVER_PORTS().get("RefereeSite");
    }
    
    /**
    * Communicate method to communicate with the Playground
    */
    private WrapperMessage communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a gam   e".
    */
    @Override
    public void annouceNewGame() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game".
    */
    @Override
    public void declareGameWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
    * In referee life cycle, transition between "end of a game" and "end of the match".
    */
    @Override
    public void declareMatchWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * End of the match.
     * @return if has endeed or not.
     */
    @Override
    public boolean endOfMatch() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getBoolean();
    }

    /**
     * The referee waits to be informed by the team A and B.
     */
    @Override
    public void waitForInformReferee() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The referee will wait fot the last contestant in operation amDone
     * when the trial has come to an end.
     */
    @Override
    public void waitForAmDone() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The referee waits for the contestant to be positioned.
     */
    @Override
    public void waitAllPositioned() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The referee is waken up by the last of the coaches in operation 
     * "informReferee" when the teams are ready to proceed. Coach method.
     * @param team "A" or "B"
     */
    @Override
    public void informReferee(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }
    
    /**
     * The referee is waken up by the last of the contestants in operation amDone
     * when the trial has come to an end.
     */
    @Override
    public void amDone() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The contestant notify the referee that is positioned.
     */
    @Override
    public void positioned() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
}
