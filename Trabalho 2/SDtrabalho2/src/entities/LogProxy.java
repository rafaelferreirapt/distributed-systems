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

    @Override
    public void initRefereeState(RefereeState state) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.initRefereeState, state));
    }

    @Override
    public void newGame() {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.newGame));
    }

    @Override
    public void newTrial() {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.newTrial));
    }

    @Override
    public int getNumberOfGames() {
        WrapperMessage result = ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                    NodeSetts.refereeSiteServerPort, new Message(MessageType.getNumberOfGames));
        return result.getMessage().getInteger();
    }

    @Override
    public int getTotalNumberOfGames() {
        WrapperMessage result = ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                    NodeSetts.refereeSiteServerPort, new Message(MessageType.getTotalNumberOfGames));
        return result.getMessage().getInteger();
    }

    @Override
    public void printGameWinner() {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.printGameWinner));
    }

    @Override
    public void newGame(int gameNumber) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.printGameWinner, gameNumber));
    }

    @Override
    public void setRefereeState(RefereeState state) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.printGameWinner, state));
    }

    @Override
    public void declareMatchWinner() {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.declareMatchWinner));
    }

    @Override
    public void initCoachState(String team, CoachState state) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.initCoachState, state, team));
    }

    @Override
    public void refreshStrengths(String team) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.refreshStrengths, team));
    }

    @Override
    public void setCoachState(String team, CoachState state) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.setCoachState, state, team));
    }

    @Override
    public void initContestant(ContestantState state, String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.initContestant, state, team, contestant));
    }

    @Override
    public void setContestantLastTrial(String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void removePosition(String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void setPosition(String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.setContestantLastTrial, team, contestant));
    }

    @Override
    public void setContestantState(ContestantState state, String team, int contestant) {
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.setContestantState, team, contestant));
    }

    @Override
    public int assertTrialDecision() {
        WrapperMessage result = ClientProxyWrapper.connect(NodeSetts.refereeSiteServerName, 
                    NodeSetts.refereeSiteServerPort, new Message(MessageType.assertTrialDecision));
        return result.getMessage().getInteger();
    }
    
    public void writeEnd(){
        ClientProxyWrapper.connect(NodeSetts.logServerName, 
                NodeSetts.logServerPort, new Message(MessageType.writeEnd));
    }
}