/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Referee extends Thread {
    
    private RefereeState state;
    private final int id;
    private final Log log;
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r, int id, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.setName("Referee " + id);
        this.id = id;
        this.log = log;
        state = RefereeState.START_OF_THE_MATCH;
    }
    
    private boolean TEST_CONDITION = true;
    private boolean OTHER_CONTIDITON = false;
    
    public void teste(){
        while(TEST_CONDITION){
            referee_site.waitForAmDone();
            
            if(true){
                this.TEST_CONDITION = false;
                this.OTHER_CONTIDITON = true;
            }
        }
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            if(this.state.getState().equals(RefereeState.START_OF_THE_MATCH.toString())){
                this.referee_site.annouceNewGame();
                this.setState(RefereeState.START_OF_A_GAME);
            }else if(this.state.getState().equals(RefereeState.START_OF_A_GAME.toString())){
                this.bench.callTrial();
                this.setState(RefereeState.TEAMS_READY);
            }else if(this.state.getState().equals(RefereeState.TEAMS_READY.toString())){
                this.referee_site.waitForInformReferee();
                this.playground.startTrial();
                this.setState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
            }else if(this.state.getState().equals(RefereeState.WAIT_FOR_TRIAL_CONCLUSION.toString())){
                this.referee_site.waitForAmDone();
                
                this.bench.assertTrialDecision();
                this.playground.assertTrialDecision();
                
                this.referee_site.declareGameWinner();
                
                this.setState(RefereeState.END_OF_A_GAME);
            }else if(this.state.getState().equals(RefereeState.END_OF_A_GAME.toString())){
                //verificação estado do jogo para saber se anuncia um novo ou nao
                this.setState(RefereeState.START_OF_A_GAME);
                this.setState(RefereeState.END_OF_THE_MATCH);
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
