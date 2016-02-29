/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author
 */
public class Referee {
    
    private playground.IReferee playground;
    private bench.IReferee bench;
    private referee_site.IReferee referee_site;
    
    public Referee(playground.IReferee p, bench.IReferee b, referee_site.IReferee r){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
    }
    
}
