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
    private final Log log;
    private playground.IContestant playground;
    private bench.IContestant bench;
    private referee_site.IContestant referee_site;
    
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r, int id, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.setName("Contestant " + id);
        this.id = id;
        this.log = log;
        state = ContestantState.SEAT_AT_THE_BENCH;
    }
    
    @Override
    public void run(){
        
    }
    
    public void setState(ContestantState state){
        this.state = state;
    }
    
    public ContestantState getCurrentState(){
        return this.state;
    }
}
