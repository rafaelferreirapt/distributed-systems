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
public class Referee extends Thread {
    
    private RefereeState state;
    private final int id;
    private final Log log;
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r, int id, Log log){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
        this.setName("Referee " + id);
        this.id = id;
        this.log = log;
        state = RefereeState.START_OF_THE_MATCH;
    }
    
    private boolean TEST_CONDITION = true;
    private boolean OTHER_CONTIDITON = false;
    
    public void teste(){
        while(TEST_CONDITION){
            referee_site.waitForAmDone();
            
            if(true){
                this.TEST_CONDITION = false;
                this.OTHER_CONTIDITON = true;
            }
        }
    }
    
    @Override
    public void run(){
        
    }
    
    public void setState(RefereeState state){
        this.state = state;
    }
    
    public RefereeState getCurrentState(){
        return this.state;
    }
}
