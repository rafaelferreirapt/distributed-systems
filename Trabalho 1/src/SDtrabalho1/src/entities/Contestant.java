/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import general_info_repo.Log;
import java.lang.Math;

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
    private static final int MAX_STRENGTH = 5;
    private static final int MIN_STRENGTH = 5;
    private int strength;
    
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r, int id, String team, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.team = team;
        this.setName("Contestant " + id + " of the team " + team);
        this.id = id;
        this.log = log;
        state = ContestantState.SEAT_AT_THE_BENCH;
        this.strength = (int)Math.ceil((Math.random() * (MAX_STRENGTH - MIN_STRENGTH + 1)) + MIN_STRENGTH);
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            if(this.state.getState().equals(ContestantState.DO_YOUR_BEST.toString())){
                this.playground.pullTheRope();
                // only the last one informs the referee
                this.referee_site.amDone();
                this.playground.waitForAssertTrialDecision();
                this.bench.seatDown();
                this.setState(ContestantState.SEAT_AT_THE_BENCH);
            }else if(this.state.getState().equals(ContestantState.SEAT_AT_THE_BENCH.toString())){
                this.bench.waitForCallContestants();
                this.bench.followCoachAdvice();
                this.setState(ContestantState.STAND_IN_POSITION);
            }else if(this.state.getState().equals(ContestantState.STAND_IN_POSITION.toString())){
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
