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
public class Coach extends Thread {
    
    private CoachState state;
    private final int id;
    private final Log log;
    private bench.ICoach bench;
    private referee_site.ICoach referee_site;
    
    public Coach(bench.ICoach b, referee_site.ICoach r, int id, Log log){
        this.bench = b;
        this.referee_site = r;
        this.setName("Coach " + id);
        this.id = id;
        this.log = log;
        state = CoachState.WAIT_FOR_REFEREE_COMMAND;
    }
    
    /**
     * This function represents the life cycle of Coach.
     */
    
    @Override
    public void run(){
        /*
        while(!referee_site.endOfMatch()){
            bench.waitForCallTrial();
        }
        */
    }
    
    public void setState(CoachState state){
        this.state = state;
    }
    
    public CoachState getCurrentState(){
        return this.state;
    }
}
