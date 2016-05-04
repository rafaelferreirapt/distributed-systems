/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

import structures.ContestantState;


/**
 * Contestant instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Contestant  extends Thread {
    
    private ContestantState state;
    
    private final int id;
    private final String team;
    
    private final interfaces.playground.IContestant playground;
    private final interfaces.bench.IContestant bench;
    private final interfaces.referee_site.IContestant referee_site;
    private final interfaces.log.IContestant log;
    
    /**
     * It will be passed to the Contestant the methods of the bench and referee site
     * that the contestant have acess. The team is the contestant team and the ID
     * is very important to know the identity of the contestant.
     * @param p Instance that implements playground contestant methods.
     * @param b Instance that implements bench contestant methods.
     * @param r Instance that implements referee site contestant methods.
     * @param id Contestant identifier.
     * @param team Team identifier, can be A or B.
     * @param l
     */
    public Contestant(interfaces.playground.IContestant p, interfaces.bench.IContestant b, interfaces.referee_site.IContestant r, int id, String team,
            interfaces.log.IContestant l){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = l;
        
        this.team = team;
        this.id = id;
        
        this.setName("Contestant " + id + " of the team " + team);
    
        state = ContestantState.SEAT_AT_THE_BENCH;
        
        this.log.initContestant(this.state,this.team, this.id);
    }
    
    /**
     * Get contestant team
     * @return team, can be "A" or "B"
     */
    public String getTeam(){
        return this.team;
    }
    
    /**
     * Get contestant ID
     * @return contestant ID.
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