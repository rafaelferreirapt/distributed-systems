/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

/**
 *
 * @author
 */
public interface IReferee {
    
    /**
     * the coaches are waken up by the referee in operation callTrial to start 
     * selecting next team
     */
    public void callTrial();

    /**
     * the coaches are waken up in operation assertTrialDecision by the referee
     * the contestants are made to sleep for a random time interval in the 
     * simulation they block next and are waken up in operation assertTrialDecision by the referee
     */
    public void assertTrialDecision();
    
}
