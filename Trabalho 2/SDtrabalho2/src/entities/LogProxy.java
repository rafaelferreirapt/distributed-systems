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
        communicate(new Message(MessageType.initRefereeState, state));
    }

    @Override
    public void newGame() {
        communicate( new Message(MessageType.newGame));
    }

    @Override
    public void newTrial() {
        communicate(new Message(MessageType.newTrial));
    }

    @Override
    public int getNumberOfGames() {
        WrapperMessage result = communicate(new Message(MessageType.getNumberOfGames));
        return result.getMessage().getInteger();
    }

    @Override
    public int getTotalNumberOfGames() {
        WrapperMessage result = communicate(new Message(MessageType.getTotalNumberOfGames));
        return result.getMessage().getInteger();
    }

    @Override
    public void printGameWinner() {
        communicate(new Message(MessageType.printGameWinner));
    }

    @Override
    public void newGame(int gameNumber) {
        communicate(new Message(MessageType.printGameWinner, gameNumber));
    }

    @Override
    public void setRefereeState(RefereeState state) {
        communicate(new Message(MessageType.printGameWinner, state));
    }

    @Override
    public void declareMatchWinner() {
        communicate(new Message(MessageType.declareMatchWinner));
    }

    @Override
    public void initCoachState(String team, CoachState state) {
        communicate(new Message(MessageType.initCoachState, state, team));
    }

    @Override
    public void refreshStrengths(String team) {
        communicate(new Message(MessageType.refreshStrengths, team));
    }

    @Override
    public void setCoachState(String team, CoachState state) {
        communicate(new Message(MessageType.setCoachState, state, team));
    }

    @Override
    public void initContestant(ContestantState state, String team, int contestant) {
        communicate(new Message(MessageType.initContestant, state, team, contestant));
    }

    @Override
    public void setContestantLastTrial(String team, int contestant) {
        communicate(new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void removePosition(String team, int contestant) {
        communicate(new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void setPosition(String team, int contestant) {
        communicate(new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void setContestantState(ContestantState state, String team, int contestant) {
        communicate(new Message(MessageType.setContestantState, team, contestant));
    }

    @Override
    public int assertTrialDecision() {
        WrapperMessage result = communicate(new Message(MessageType.assertTrialDecision));
        return result.getMessage().getInteger();
    }
    
    public void writeEnd(){
        communicate(new Message(MessageType.writeEnd));
    }
}