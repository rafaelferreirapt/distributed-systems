/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities.contestant;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.Constants;
import structures.ContestantState;
import structures.VectorTimestamp;


/**
 * Contestant instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Contestant  extends Thread {
    
    private ContestantState state;
    
    private final int id;
    private final int totalID;
    private final String team;
    
    private final interfaces.playground.IContestant playground;
    private final interfaces.bench.IContestant bench;
    private final interfaces.referee_site.IContestant referee_site;
    private final interfaces.log.IContestant log;
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    
    /**
     * It will be passed to the Contestant the methods of the bench and referee site
     * that the contestant have acess. The team is the contestant team and the ID
     * is very important to know the identity of the contestant.
     * @param p Instance that implements playground contestant methods.
     * @param b Instance that implements bench contestant methods.
     * @param r Instance that implements referee site contestant methods.
     * @param id Contestant identifier.
     * @param totalID
     * @param team Team identifier, can be A or B.
     * @param l
     */
    public Contestant(interfaces.playground.IContestant p, interfaces.bench.IContestant b, interfaces.referee_site.IContestant r, int id, int totalID, String team,
            interfaces.log.IContestant l){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.log = l;
        
        this.team = team;
        this.id = id;
        this.totalID = totalID;
        
        this.setName("Contestant " + id + " of the team " + team);
    
        state = ContestantState.SEAT_AT_THE_BENCH;
        
        try {
            this.log.initContestant(this.state,this.team, this.id);
        } catch (RemoteException ex) {
            Logger.getLogger(Contestant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.team.equals("A")){
            this.myClock = new VectorTimestamp(Constants.N_COACHS + Constants.N_CONTESTANTS_TEAM*2 + 2, this.totalID + 2);
        }else{
            this.myClock = new VectorTimestamp(Constants.N_COACHS + Constants.N_CONTESTANTS_TEAM*2 + 2, this.totalID + 3);
        }

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
        try {
            while(!this.referee_site.endOfMatch()){
                    switch(this.state){
                        case DO_YOUR_BEST:
                            this.playground.pullTheRope(this.id, this.team);

                            this.log.setContestantLastTrial(this.team, this.id);
                            
                            this.myClock.increment();
                            this.receivedClock = this.referee_site.amDone(this.myClock.clone());
                            this.myClock.update(this.receivedClock);

                            this.myClock.increment();
                            this.receivedClock = this.playground.waitForAssertTrialDecision(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                            
                            this.log.removePosition(this.team, this.id, this.myClock.clone());

                            this.myClock.increment();
                            this.receivedClock = this.bench.seatDown(this.team, this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                            
                            this.state = ContestantState.SEAT_AT_THE_BENCH;

                            break;
                        case SEAT_AT_THE_BENCH:
                            this.myClock.increment();
                            this.receivedClock = this.bench.waitForCallContestants(this.team, this.id, this.myClock.clone()); // espera que o treinador o chame
                            this.myClock.update(this.receivedClock);
                            
                            if(this.referee_site.endOfMatch()){
                                break;
                            }

                            this.myClock.increment();
                            this.receivedClock = this.bench.followCoachAdvice(this.team, this.id, this.myClock.clone()); // o ultimo informa o utilizador
                            this.myClock.update(this.receivedClock);
                            
                            this.log.setPosition(this.team, this.id, this.myClock.clone());

                            this.state = ContestantState.STAND_IN_POSITION;
                            break;
                        case STAND_IN_POSITION:
                            this.myClock.increment();
                            this.receivedClock = this.referee_site.positioned(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                            
                            this.myClock.increment();
                            this.receivedClock = this.playground.waitForStartTrial(this.myClock.clone());
                            this.myClock.update(this.receivedClock);
                            this.state = ContestantState.DO_YOUR_BEST;
                            break;
                    }
                    if(!referee_site.endOfMatch()){
                        this.log.setContestantState(state, team, this.id, this.myClock.clone());
                    }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Contestant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
