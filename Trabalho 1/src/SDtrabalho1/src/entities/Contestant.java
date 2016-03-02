/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Contestant  extends Thread {
    
    private playground.IContestant playground;
    private bench.IContestant bench;
    private referee_site.IContestant referee_site;
    
    public Contestant(playground.IContestant p, bench.IContestant b, referee_site.IContestant r){
        this.playground = p;
        this.bench = b;
        this.referee_site = r;
    }
}
