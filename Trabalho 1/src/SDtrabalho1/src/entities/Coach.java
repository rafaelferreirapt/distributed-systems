/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Coach extends Thread {
    
    private bench.ICoach bench;
    private referee_site.ICoach referee_site;
    
    public Coach(bench.ICoach b, referee_site.ICoach r){
        this.bench = b;
        this.referee_site = r;
    }
    
}
