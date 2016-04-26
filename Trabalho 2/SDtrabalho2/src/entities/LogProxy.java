/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import general_info_repo.ICoach;
import general_info_repo.IContestant;
import general_info_repo.IReferee;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxy;
import settings.NodeSettsProxy;

/**
 * Log Proxy
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class LogProxy implements IReferee, ICoach, IContestant{

    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    /**
    * Log Proxy
    */
    public LogProxy(){
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_HOST = proxy.SERVER_HOSTS().get("Log");
        SERVER_PORT = proxy.SERVER_PORTS().get("Log");
    }
    
    /**
    * Communicate method to communicate with the Log
    */
    private WrapperMessage communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
     * Init the referee state.
     * @param state referee state.
     */
    @Override
    public void initRefereeState(RefereeState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }
    
    /**
     * This method will be called every time that one game is started
     */
    @Override
    public void newGame() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * New trial with the game.
     */
    @Override
    public void newTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * Number of games played.
     * @return number of games played.
     */
    @Override
    public int getNumberOfGames() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }
    
    /**
     * Total number of games.
     * @return total number of games played.
     */
    @Override
    public int getTotalNumberOfGames() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }

    /**
     * Print game winner.
     */
    @Override
    public void printGameWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
    
    /**
     * This method will be called every time that one game is started
     * @param gameNumber the atual game number.
     */
    @Override
    public void newGame(int gameNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, gameNumber));
    }

    /**
     * Set the referee state.
     * @param state referee state.
     */
    @Override
    public void setRefereeState(RefereeState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }

    /**
     * Declare match winner.
     */
    @Override
    public void declareMatchWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    /**
     * Init coach.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    @Override
    public void initCoachState(String team, CoachState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team));
    }

    /**
     * Refresh strengths of the team, the coach calls this method.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public void refreshStrengths(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    /**
     * Set coach state.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    @Override
    public void setCoachState(String team, CoachState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team));
    }

    /**
     * Init the contestant with the initial state, team and contestant.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public void initContestant(ContestantState state, String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team, contestant));
    }

    /**
     * Set the contestant last trial, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public void setContestantLastTrial(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    /**
     * Remove position of the player.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public void removePosition(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    /**
     * Set position, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public void setPosition(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    /**
     * Update the contestant state.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public void setContestantState(ContestantState state, String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team, contestant));
    }

    /**
     * Assert trial decision.
     * @return decision, can be 2 (B), -2 (A) for knock outs; 0 for game finished with win or draw
     * or 1 for new trial.
     */
    @Override
    public int assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }
    
    /**
     * This method will be called to finish write the logging file.
     */
    public void writeEnd(){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
}