/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /* CONTESTANTS METHODS */

    /**
     * the coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     */
    public void followCoachAdvice();
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     */
    public void seatDown();
    
    public void waitForCallContestants();
    
}
