/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface ICoach {

    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command"
     */
    public void reviewNotes();

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team"
     */
    public void callContestants();
    
    public void waitForCallTrial();
    
    public void waitForAssertTrialDecision();
    
    public void waitForFollowCoachAdvice();
}
