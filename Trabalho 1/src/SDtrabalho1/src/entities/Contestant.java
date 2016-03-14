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
public class Contestant  extends Thread {
    
    private ContestantState state;
    private final int id;
    private final String team;
    private final Log log;
    
    private playground.IContestant playground;
    private bench.IContestant bench;
    private referee_site.IContestant referee_site;
    private Match match;
    
    private static final int MAX_STRENGTH = 5;
    private static final int MIN_STRENGTH = 1;
    private int strength;
    
    private int playedTrials = 0;
    
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r, int id, String team, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.team = team;
        this.setName("Contestant " + id + " of the team " + team);
        this.id = id;
        this.log = log;
        this.match = Match.getInstance();
        state = ContestantState.SEAT_AT_THE_BENCH;
        this.strength = (int)Math.ceil((Math.random() * (MAX_STRENGTH - MIN_STRENGTH + 1)) + MIN_STRENGTH);
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
            if(this.state.getState().equals(ContestantState.DO_YOUR_BEST.getState())){
                this.playedTrials++;
                int now_strength = this.strength-this.playedTrials+(this.match.trials_played-this.playedTrials);
                
                if(now_strength > 5){
                    now_strength = 5;
                }else if(now_strength < 1){
                    now_strength = 1;
                }
                
                this.playground.pullTheRope(now_strength, this.team);
                this.referee_site.amDone();

                this.playground.waitForAssertTrialDecision();
                this.bench.seatDown(this.team);
                this.setState(ContestantState.SEAT_AT_THE_BENCH);
            }else if(this.state.getState().equals(ContestantState.SEAT_AT_THE_BENCH.getState())){
                System.err.println("A ir para waiting " + this.team);
                this.bench.waitForCallContestants(this.team, this.id); // espera que o treinador o chame
                System.err.println("Waited: " + this.id + " " + this.team);
                if(this.referee_site.endOfMatch()){
                    break;
                }
                this.bench.followCoachAdvice(this.team, this.id); // o ultimo informa o utilizador
                System.err.println("Followed: " + this.id + " " + this.team);
                this.setState(ContestantState.STAND_IN_POSITION);
            }else if(this.state.getState().equals(ContestantState.STAND_IN_POSITION.getState())){
                this.playground.waitForStartTrial();
                this.playground.getReady();
                this.setState(ContestantState.DO_YOUR_BEST);
            }
        }
    }
    
    public void setState(ContestantState state){
        this.state = state;
    }
    
    public ContestantState getCurrentState(){
        return this.state;
    }
}
