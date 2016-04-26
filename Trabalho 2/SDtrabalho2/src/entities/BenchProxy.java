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
import communication.proxy.ClientProxy;
import settings.NodeSettsProxy;

/**
 * Bench Proxy to make requests to the bench server
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class BenchProxy implements IReferee, ICoach, IContestant{
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
     * Bench Proxy
     */
    public BenchProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Bench");
        SERVER_PORT = proxy.SERVER_PORTS().get("Bench");
    }
    
    /**
     * Communicate to make connection with the server
     */
    private void communicate(Message m){
        ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
     * The referee must wait that all the coaches are waiting for the call trial
     * and all the contestants are in the bench, then the referee can continue 
     * the trial, it will reset all the flags, the flag to the contestants selected
     * in the Team A or B and set the coaches wait for call trial to zero. The
     * coachs are sleeping and need the flag callTrialTaken true to procede. In the
     * final there is a notifyAll() to wake up all the entities in the Bench.
     */
    @Override
    public void callTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The referee will waken up the coaches with the trialDecisionTaken = true and 
     * with the notifyAll().
     */
    @Override
    public void assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
    /**
     * This method will wakeup all the entities in the bench and declare that
     * the match was ended.
     */
    @Override
    public void wakeUp() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command".
     * The coach will wait until 10 contestants are in the bench and then will continue.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public void reviewNotes(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team".
     * The coach will select randomly the contestants, this will be the strategy, select random
     * the contestants. The contestants will make one "pool" to see if they are in the array of
     * the players selected.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public void callContestants(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }
    
    /**
     * The coaches are sleeping in this method waiting that the referee inform  
     * and can select the next team.
     */
    @Override
    public void waitForCallTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * The coaches will wait until the referee make the decision of notify the
     * referees in the assertTrialDecision.
     */
    @Override
    public void waitForAssertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
    /**
     * The coach will wait until the last contestant stand in position to then 
     * follow the coach advice. The flags will be resetted.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public void waitForFollowCoachAdvice(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }
    
    /**
     * The coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position.
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    @Override
    public void followCoachAdvice(String team, int idC) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, idC));
    }

    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     * @param team Team identifier, can be A or B.
     */
    @Override
    public void seatDown(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    /**
     * The contestants will wait here to be called to the trial in the bench.
     * If the the team YXZ is select, then the contestant will verify if there is
     * in the array of selected contestants to do the best in the rope game.
     * When the contestant leaves the bench then is decremented one variable that
     * conunts how many contestants there is in the bench. One more important thing,
     * we need the ID of the last contestant that is up to then inform the coach that
     * all the contestants are ready to go.
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    @Override
    public void waitForCallContestants(String team, int idC) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, idC));
    }
}
