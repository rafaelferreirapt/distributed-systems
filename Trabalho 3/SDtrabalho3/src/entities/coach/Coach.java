/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities.coach;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.CoachState;
import structures.Constants;
import structures.VectorTimestamp;


/**
 * Coach instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Coach extends Thread {
    
    private CoachState state;
    
    private final String team;
    private final int id;

    private final interfaces.bench.ICoach bench;
    private final interfaces.referee_site.ICoach referee_site;
    private final interfaces.log.ICoach log;
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;


    
    /**
     * It will be passed to the Coach the methods of the bench and referee site
     * that the coach have acess. The team is the coach team, very important to know
     * the identity of the coach.
     * @param b Instance that implements bench coach methods.
     * @param r Instance that implements referee site coach methods.
     * @param team Team identifier, can be A or B.
     * @param id
     * @param l
     * @throws java.rmi.RemoteException
     */
    public Coach(interfaces.bench.ICoach b, interfaces.referee_site.ICoach r, String team, int id, interfaces.log.ICoach l){
        this.bench = b;
        this.referee_site = r;
        this.log = l;
        this.id = id;
        
        this.team = team;
        
        this.setName("Coach of the team " + team);
        state = CoachState.WAIT_FOR_REFEREE_COMMAND;
        
        try {
            this.log.initCoachState(team, state);
        } catch (RemoteException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(this.team.equals("A")){
            this.myClock = new VectorTimestamp(Constants.N_COACHS + Constants.N_CONTESTANTS_TEAM*2 + 2, this.id + 1);
        }else{
            this.myClock = new VectorTimestamp(Constants.N_COACHS + Constants.N_CONTESTANTS_TEAM*2 + 2, Constants.N_CONTESTANTS_TEAM + this.id + 1);
        }

    }
    
    /**
     * This function represents the life cycle of Coach.
     */
    @Override
    public void run(){
        try {
            while(!this.referee_site.endOfMatch()){
                switch(this.state){
                    case ASSEMBLE_TEAM:
                        this.myClock.increment();
                        this.receivedClock = this.bench.waitForFollowCoachAdvice(this.team, this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        
                        this.myClock.increment();
                        this.receivedClock = this.referee_site.informReferee(this.team, this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        
                        this.state = CoachState.WATCH_TRIAL;
                        break;
                    case WAIT_FOR_REFEREE_COMMAND:
                        this.myClock.increment();
                        this.receivedClock = this.bench.waitForCallTrial(this.myClock.clone());
                        this.myClock.update(this.receivedClock);

                        if(this.referee_site.endOfMatch()){
                            break;
                        }

                        this.myClock.increment();
                        this.receivedClock = this.bench.callContestants(this.team, this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        
                        this.state = CoachState.ASSEMBLE_TEAM;
                        break;
                    case WATCH_TRIAL:
                        this.myClock.increment();
                        this.receivedClock = this.bench.waitForAssertTrialDecision(this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        
                        this.myClock.increment();
                        this.receivedClock = this.bench.reviewNotes(this.team, this.myClock.clone());
                        this.myClock.update(this.receivedClock);
                        
                        this.log.refreshStrengths(this.team);

                        this.state = CoachState.WAIT_FOR_REFEREE_COMMAND;
                        break;
                }
                if(!referee_site.endOfMatch()){
                    this.log.setCoachState(team, state, this.myClock.clone());
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Coach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
