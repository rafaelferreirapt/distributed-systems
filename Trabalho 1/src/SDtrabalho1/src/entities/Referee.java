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
    
    public enum RefereeState{
        START_OF_THE_MATCH, START_OF_A_GAME, TEAMS_READY, WAIT_FOR_TRIAL_CONCLUSION, 
        END_OF_A_GAME, END_OF_THE_MATCH
    }
    
    private RefereeState state;
    
    private final Log log;
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    private Match match;
    private int centre_of_the_rope = 0;
    private int numberOfGames = 1000000;
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
            switch(this.state){
                case START_OF_THE_MATCH:
                    this.match = Match.getInstance();
                    this.referee_site.annouceNewGame();
                    this.state = RefereeState.START_OF_A_GAME;
                    break;
                case START_OF_A_GAME:
                    this.match.newGame();
                    this.match.newTrial(0);
                    //System.err.println("New Trial");
                    this.bench.callTrial(); // vai acordar os treinadores <= esperar que os jogadores estejam todos sentados
                    this.state = RefereeState.TEAMS_READY;
                    break;
                case TEAMS_READY:
                    this.referee_site.waitForInformReferee(); // esperar que o treinador o informe
                    this.playground.startTrial();
                    this.state = RefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    break;
                case WAIT_FOR_TRIAL_CONCLUSION:
                    this.referee_site.waitForAmDone(); // uultimo jogador tem de avisar o arbitro

                    if(this.playground.getTrialState() >= 4){ // knock out B
                        this.bench.assertTrialDecision();
                        this.playground.assertTrialDecision();

                        this.referee_site.declareGameWinner();
                        this.state = RefereeState.END_OF_A_GAME;
                    }else if(this.playground.getTrialState() <= -4){ // knock out A
                        this.bench.assertTrialDecision();
                        this.playground.assertTrialDecision();

                        this.referee_site.declareGameWinner();
                        this.state = RefereeState.END_OF_A_GAME;
                    }else if(this.playground.getTrialState() > 0){ 
                        this.bench.assertTrialDecision();
                        this.playground.assertTrialDecision();

                        this.match.setPontuation(this.match.getPontuation("B")+1, "B");

                        if(this.match.gameNumberOfTrials() >= 6){
                            this.referee_site.declareGameWinner();
                            this.state = RefereeState.END_OF_A_GAME;
                        }else{
                            this.centre_of_the_rope = this.playground.getTrialState();
                            this.match.newTrial(centre_of_the_rope);
                            //System.err.println("New Trial");
                            this.bench.callTrial();
                            this.state = RefereeState.TEAMS_READY;
                        }
                    }else if(this.playground.getTrialState() < 0){
                        this.bench.assertTrialDecision();
                        this.playground.assertTrialDecision();

                        this.match.setPontuation(this.match.getPontuation("A")+1, "A");

                        if(this.match.gameNumberOfTrials() >= 6){
                            this.referee_site.declareGameWinner();
                            this.state = RefereeState.END_OF_A_GAME;
                        }else{
                            this.centre_of_the_rope = this.playground.getTrialState();
                            this.match.newTrial(centre_of_the_rope);
                            //System.err.println("New Trial");
                            this.bench.callTrial();
                            this.state = RefereeState.TEAMS_READY;
                        }
                    }else if(this.playground.getTrialState() == 0){
                        this.bench.assertTrialDecision();
                        this.playground.assertTrialDecision();

                        if(this.match.gameNumberOfTrials() >= 6){
                            this.referee_site.declareGameWinner();
                            this.state = RefereeState.END_OF_A_GAME;
                        }else{
                            this.centre_of_the_rope = this.playground.getTrialState();
                            this.match.newTrial(centre_of_the_rope);
                            //System.err.println("New Trial");
                            this.bench.callTrial();
                            this.state = RefereeState.TEAMS_READY;
                        }
                    }
                    
                    break;
                case END_OF_A_GAME:
                    a++;
                    System.out.println("Ended game: " + a);

                    if(this.match.getNumberOfGames() < numberOfGames){
                        this.referee_site.annouceNewGame();
                        this.state = RefereeState.START_OF_A_GAME;
                    }else{
                        this.referee_site.declareMatchWinner();
                        this.bench.wakeUp();
                        System.out.println("END MATCH");
                        this.state = RefereeState.END_OF_THE_MATCH;
                    }
                    break;
            }
        }
    }
}
