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
    private int numberOfGames = 100000;
    private int a = 0;
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = log;
        this.setName("Referee");
        state = RefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            if(this.state.getState().equals(RefereeState.START_OF_THE_MATCH.getState())){
                this.match = Match.getInstance();
                this.referee_site.annouceNewGame();
                this.setState(RefereeState.START_OF_A_GAME);
            }else if(this.state.getState().equals(RefereeState.START_OF_A_GAME.getState())){
                this.match.newGame();
                this.match.newTrial(0);
                System.err.println("New Trial");
                this.bench.callTrial(); // vai acordar os treinadores <= esperar que os jogadores estejam todos sentados
                this.setState(RefereeState.TEAMS_READY);
            }else if(this.state.getState().equals(RefereeState.TEAMS_READY.getState())){
                this.referee_site.waitForInformReferee(); // esperar que o treinador o informe
                this.playground.startTrial();
                this.setState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
            }else if(this.state.getState().equals(RefereeState.WAIT_FOR_TRIAL_CONCLUSION.getState())){
                this.referee_site.waitForAmDone(); // uultimo jogador tem de avisar o arbitro

                if(this.playground.getTrialState() >= 4){ // knock out B
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    this.referee_site.declareGameWinner();
                    this.setState(RefereeState.END_OF_A_GAME);
                }else if(this.playground.getTrialState() <= -4){ // knock out A
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
                        this.match.newTrial(centre_of_the_rope);
                        System.err.println("New Trial");
                        this.bench.callTrial();
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
                        this.match.newTrial(centre_of_the_rope);
                        System.err.println("New Trial");
                        this.bench.callTrial();
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
                        this.match.newTrial(centre_of_the_rope);
                        System.err.println("New Trial");
                        this.bench.callTrial();
                        this.setState(RefereeState.TEAMS_READY);
                    }
                }
            }else if(this.state.getState().equals(RefereeState.END_OF_A_GAME.getState())){
                a++;
                System.out.println("Ended game: " + a);
                
                if(this.match.getNumberOfGames() < numberOfGames){
                    this.referee_site.annouceNewGame();
                    this.setState(RefereeState.START_OF_A_GAME);
                }else{
                    this.referee_site.declareMatchWinner();
                    this.bench.wakeUp();
                    System.out.println("END MATCH");
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
