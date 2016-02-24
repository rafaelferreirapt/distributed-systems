/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author
 */
public interface IReferee {
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion"
     */
    public void startTrial();
    
    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion"
     */
    public void assertTrialDecision();
}
