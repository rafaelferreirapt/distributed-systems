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
public class Contestant  extends Thread {
    
    private ContestantState state;
    
    private final int id;
    private final String team;
    private final Log log;
    
    private final playground.IContestant playground;
    private final bench.IContestant bench;
    private final referee_site.IContestant referee_site;
    
    private static final int MAX_STRENGTH = 4;
    private static final int MIN_STRENGTH = 1;
    private int strength;
    
    private int lastTrial = 0, gamesInBench = 0;
    
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r, int id, String team){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = Log.getInstance();
        
        this.team = team;
        this.id = id;
        
        this.setName("Contestant " + id + " of the team " + team);
        
        this.strength = (int)Math.ceil(Math.random() * MAX_STRENGTH + MIN_STRENGTH);
        state = ContestantState.SEAT_AT_THE_BENCH;
        
        this.log.initContestant(this.state, this.strength, this.team, this.id);
    }
    
    public String getTeam(){
        return this.team;
    }
    
    public int getID(){
        return this.id;
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            switch(this.state){
                case DO_YOUR_BEST:
                    this.gamesInBench = this.log.getTrials_played() - this.lastTrial;
                    if(this.gamesInBench > 0){
                        this.strength = this.strength + this.gamesInBench;
                    }
                    
                    if(this.strength > 5){
                        this.strength = 5;
                    }else if(this.strength < 1){
                        this.strength = 1;
                    }
                    
                    this.log.setContestantStrength(this.strength, this.team, this.id);
                    
                    this.playground.pullTheRope(this.strength, this.team);
                    if(this.strength > 1){
                        this.strength -= 1;
                    }

                    this.lastTrial = this.log.getTrials_played() + 1;
                    this.referee_site.amDone();

                    this.playground.waitForAssertTrialDecision();
                    this.bench.seatDown(this.team);
                    this.state = ContestantState.SEAT_AT_THE_BENCH;
                    break;
                case SEAT_AT_THE_BENCH:

                    this.bench.waitForCallContestants(this.team, this.id); // espera que o treinador o chame
                    
                    if(this.referee_site.endOfMatch()){
                        break;
                    }
                    
                    this.bench.followCoachAdvice(this.team, this.id); // o ultimo informa o utilizador
                    this.state = ContestantState.STAND_IN_POSITION;
                    break;
                case STAND_IN_POSITION:
                    this.playground.waitForStartTrial();
                    this.state = ContestantState.DO_YOUR_BEST;
                    break;
            }
            this.log.setContestantState(state, team, this.id);
        }
    }
}
