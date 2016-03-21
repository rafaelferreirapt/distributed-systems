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
     * @param team
     */
    public void reviewNotes(String team);

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team"
     * @param team
     */
    public void callContestants(String team);
    
    /**
     *
     */
    public void waitForCallTrial();
    
    /**
     *
     */
    public void waitForAssertTrialDecision();
    
    /**
     *
     * @param team
     */
    public void waitForFollowCoachAdvice(String team);
}
