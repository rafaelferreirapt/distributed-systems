/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Referee extends Thread {
    
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
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
}
