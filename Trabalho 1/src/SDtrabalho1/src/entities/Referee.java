/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;
import general_info_repo.Match;

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
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = log;
        this.match = Match.getInstance();

        this.setName("Referee");
        state = RefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            switch(this.state){
                case START_OF_THE_MATCH:
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
                    this.referee_site.waitForAmDone(); // ultimo jogador tem de avisar o arbitro
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();
                    
                    switch(this.match.assertTrialDecision()){
                        case -2:
                        case 2:
                        case 0:
                            this.referee_site.declareGameWinner();
                            this.state = RefereeState.END_OF_A_GAME;
                            break;
                        case 1:
                            this.bench.callTrial();
                            this.state = RefereeState.TEAMS_READY;
                            break;
                    }
                    
                    break;
                case END_OF_A_GAME:
                    System.out.println("Ended game: " + this.match.getNumberOfGames());

                    if(this.match.getNumberOfGames() < this.match.getTotalNumberOfGames()){
                        this.referee_site.annouceNewGame();
                        this.state = RefereeState.START_OF_A_GAME;
                    }else{
                        this.match.declareMatchWinner();
                        this.referee_site.declareMatchWinner();
                        this.bench.wakeUp();
                        this.state = RefereeState.END_OF_THE_MATCH;
                        System.out.println("END MATCH");
                    }
                    break;
            }
        }
    }
}
