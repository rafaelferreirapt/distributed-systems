/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package entities;

/**
 *
 * @author
 */
public class Coach {
    
    private bench.ICoach bench;
    private referee_site.ICoach referee_site;
    
    public Coach(bench.ICoach b, referee_site.ICoach r){
        this.bench = b;
        this.referee_site = r;
    }
    
}
