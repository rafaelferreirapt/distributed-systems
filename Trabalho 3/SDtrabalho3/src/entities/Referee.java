/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import structures.RefereeState;

/**
 * Referee instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Referee extends Thread {
    
    private RefereeState state;
    
    //private final Log log;
    private final interfaces.playground.IReferee playground;
    private final interfaces.bench.IReferee bench;
    private final interfaces.referee_site.IReferee referee_site;
    private final interfaces.log.IReferee log;
    
    /**
     * It will be passed to the Referee the methods of the bench and referee site
     * that the coach have acess. 
     * @param p Instance that implements playground referee methods.
     * @param b Instance that implements bench referee methods.
     * @param r Instance that implements referee site referee methods.
     * @param l
     */
    public Referee(interfaces.playground.IReferee p, interfaces.bench.IReferee b, interfaces.referee_site.IReferee r, 
            interfaces.log.IReferee l){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = l;

        this.setName("Referee");
        state = RefereeState.START_OF_THE_MATCH;
        
        this.log.initRefereeState(state);
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            switch(this.state){
                case START_OF_THE_MATCH:
                    this.referee_site.annouceNewGame();
                    this.log.newGame();

                    this.state = RefereeState.START_OF_A_GAME;
                    break;
                case START_OF_A_GAME:
                    
                    this.log.newTrial();
                    //System.err.println("New Trial");
                    this.bench.callTrial(); // vai acordar os treinadores <= esperar que os jogadores estejam todos sentados
                    this.state = RefereeState.TEAMS_READY;
                    break;
                case TEAMS_READY:
                    this.referee_site.waitForInformReferee(); // esperar que o treinador o informe
                    this.referee_site.waitAllPositioned();

                    this.playground.startTrial();
                    this.state = RefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    break;
                case WAIT_FOR_TRIAL_CONCLUSION:
                    this.referee_site.waitForAmDone(); // ultimo jogador tem de avisar o arbitro
                    this.bench.assertTrialDecision();
                    this.playground.assertTrialDecision();

                    switch(this.log.assertTrialDecision()){
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

                    System.out.println("Ended game: " + this.log.getNumberOfGames());

                    if(this.log.getNumberOfGames() < this.log.getTotalNumberOfGames()){

                        this.referee_site.annouceNewGame();
                        this.log.printGameWinner();
                        this.log.newGame();
                        this.state = RefereeState.START_OF_A_GAME;
                    }else{
                        //this.log.declareMatchWinner();
                        this.referee_site.declareMatchWinner();
                        this.bench.wakeUp();
                        this.state = RefereeState.END_OF_THE_MATCH;
                        System.out.println("END MATCH");
                    }


                    break;
            }
            this.log.setRefereeState(state);
        }
        this.log.printGameWinner();
        this.log.declareMatchWinner();
    }
}
