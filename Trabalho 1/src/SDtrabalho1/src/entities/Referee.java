/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;
import playground.Match;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Referee extends Thread {
    
    private RefereeState state;
    
    private final Log log;
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    private Match match;
    private int centre_of_the_rope = 0;
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = log;
        state = RefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            if(this.state.getState().equals(RefereeState.START_OF_THE_MATCH.toString())){
                this.match = Match.getInstance();
                this.referee_site.annouceNewGame();
                this.match.newGame();
                this.setState(RefereeState.START_OF_A_GAME);
            }else if(this.state.getState().equals(RefereeState.START_OF_A_GAME.toString())){
                this.match.newTrial(centre_of_the_rope);
                this.bench.callTrial();
                this.setState(RefereeState.TEAMS_READY);
            }else if(this.state.getState().equals(RefereeState.TEAMS_READY.toString())){
                this.referee_site.waitForInformReferee();
                this.playground.startTrial();
                this.setState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
            }else if(this.state.getState().equals(RefereeState.WAIT_FOR_TRIAL_CONCLUSION.toString())){
                this.referee_site.waitForAmDone();
                
                if(this.playground.getTrialState() >= 4){
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    this.referee_site.declareGameWinner();
                    this.setState(RefereeState.END_OF_A_GAME);
                }else if(this.playground.getTrialState() <= -4){
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    this.referee_site.declareGameWinner();
                    this.setState(RefereeState.END_OF_A_GAME);
                }else if(this.playground.getTrialState() > 0){
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    this.match.setPontuation(this.match.getPontuation("B")+1, "B");
                    
                    if(this.match.gameNumberOfTrials() >= 6){
                        this.referee_site.declareGameWinner();
                        this.setState(RefereeState.END_OF_A_GAME);
                    }else{
                        this.centre_of_the_rope = this.playground.getTrialState();
                        this.setState(RefereeState.TEAMS_READY);
                    }
                }else if(this.playground.getTrialState() < 0){
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    this.match.setPontuation(this.match.getPontuation("A")+1, "A");
                    
                    if(this.match.gameNumberOfTrials() >= 6){
                        this.referee_site.declareGameWinner();
                        this.setState(RefereeState.END_OF_A_GAME);
                    }else{
                        this.centre_of_the_rope = this.playground.getTrialState();
                        this.setState(RefereeState.TEAMS_READY);
                    }
                }else if(this.playground.getTrialState() == 0){
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    if(this.match.gameNumberOfTrials() >= 6){
                        this.referee_site.declareGameWinner();
                        this.setState(RefereeState.END_OF_A_GAME);
                    }else{
                        this.centre_of_the_rope = this.playground.getTrialState();
                        this.setState(RefereeState.TEAMS_READY);
                    }
                }
            }else if(this.state.getState().equals(RefereeState.END_OF_A_GAME.toString())){
                if(this.match.getNumberOfGames() < 3){
                    this.referee_site.annouceNewGame();
                    this.match.newGame();
                    this.setState(RefereeState.START_OF_A_GAME);
                }else{
                    this.referee_site.declareMatchWinner();
                    this.setState(RefereeState.END_OF_THE_MATCH);
                }
            }
        }
    }
    
    public void setState(RefereeState state){
        this.state = state;
    }
    
    public RefereeState getCurrentState(){
        return this.state;
    }
}
