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
    
    /**
     * It will be passed to the Contestant the methods of the bench and referee site
     * that the contestant have acess. The team is the contestant team and the ID
     * is very important to know the identity of the contestant.
     * @param p
     * @param b
     * @param r
     * @param id
     * @param team
     */
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r, int id, String team){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = Log.getInstance();
        
        this.team = team;
        this.id = id;
        
        this.setName("Contestant " + id + " of the team " + team);
    
        state = ContestantState.SEAT_AT_THE_BENCH;
        
        this.log.initContestant(this.state,this.team, this.id);
    }
    
    /**
     * Get contestant team
     * @return
     */
    public String getTeam(){
        return this.team;
    }
    
    /**
     * Get contestant ID
     * @return
     */
    public int getID(){
        return this.id;
    }
    
    @Override
    public void run(){
        while(!referee_site.endOfMatch()){
            switch(this.state){
                case DO_YOUR_BEST:
                    this.playground.pullTheRope(this.id, this.team);
                    
                    this.log.setContestantLastTrial(this.team, this.id);
                    
                    this.referee_site.amDone();

                    this.playground.waitForAssertTrialDecision();
                    this.log.removePosition(this.team, this.id);
                    
                    this.bench.seatDown(this.team);

                    this.state = ContestantState.SEAT_AT_THE_BENCH;

                    break;
                case SEAT_AT_THE_BENCH:

                    this.bench.waitForCallContestants(this.team, this.id); // espera que o treinador o chame

                    if(this.referee_site.endOfMatch()){
                        break;
                    }
                    
                    this.bench.followCoachAdvice(this.team, this.id); // o ultimo informa o utilizador
                    this.log.setPosition(this.team, this.id);

                    this.state = ContestantState.STAND_IN_POSITION;
                    break;
                case STAND_IN_POSITION:
                    this.referee_site.positioned();
                    this.playground.waitForStartTrial();
                    this.state = ContestantState.DO_YOUR_BEST;
                    break;
            }
            if(!referee_site.endOfMatch()){
                this.log.setContestantState(state, team, this.id);
            }
        }
    }
}
