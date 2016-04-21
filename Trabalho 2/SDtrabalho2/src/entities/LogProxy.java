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
import communication.proxy.ClientProxyWrapper;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class LogProxy implements IReferee, ICoach, IContestant{

    private final String SERVER_HOST = NodeSetts.SERVER_HOSTS.get("log");
    private final int SERVER_PORT = NodeSetts.SERVER_PORTS.get("log");
    
    private WrapperMessage communicate(Message m){
        return ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    @Override
    public void initRefereeState(RefereeState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }

    @Override
    public void newGame() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void newTrial() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public int getNumberOfGames() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }

    @Override
    public int getTotalNumberOfGames() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }

    @Override
    public void printGameWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void newGame(int gameNumber) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, gameNumber));
    }

    @Override
    public void setRefereeState(RefereeState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state));
    }

    @Override
    public void declareMatchWinner() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }

    @Override
    public void initCoachState(String team, CoachState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team));
    }

    @Override
    public void refreshStrengths(String team) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team));
    }

    @Override
    public void setCoachState(String team, CoachState state) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team));
    }

    @Override
    public void initContestant(ContestantState state, String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team, contestant));
    }

    @Override
    public void setContestantLastTrial(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    @Override
    public void removePosition(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    @Override
    public void setPosition(String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, team, contestant));
    }

    @Override
    public void setContestantState(ContestantState state, String team, int contestant) {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt, state, team, contestant));
    }

    @Override
    public int assertTrialDecision() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage result = communicate(new Message(mt));
        return result.getMessage().getInteger();
    }
    
    public void writeEnd(){
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        communicate(new Message(mt));
    }
}