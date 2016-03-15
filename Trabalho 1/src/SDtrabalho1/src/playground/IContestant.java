/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     */
    public void pullTheRope(int strength, String team);
    
    public void waitForStartTrial();
    
    public void waitForAssertTrialDecision();
    
}
