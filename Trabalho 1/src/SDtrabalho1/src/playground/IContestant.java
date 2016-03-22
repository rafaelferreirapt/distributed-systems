/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 * Contestant interface of Playground instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * @param id contestant identifier
     * @param team "A" or "B"
     */
    public void pullTheRope(int id, String team);
    
    /**
     * Wait for start trial. Contestant method.
     */
    public void waitForStartTrial();
    
    /**
     * Wait for assert trial decision. Contestant method.
     */
    public void waitForAssertTrialDecision();
    
}
