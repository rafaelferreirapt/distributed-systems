/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

/**
 * Contestant interface of Bench instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * The coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position.
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    public void followCoachAdvice(String team, int idC);
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     * @param team Team identifier, can be A or B.
     */
    public void seatDown(String team);
    
    /**
     * The contestants will wait here to be called to the trial in the bench.
     * If the the team YXZ is select, then the contestant will verify if there is
     * in the array of selected contestants to do the best in the rope game.
     * When the contestant leaves the bench then is decremented one variable that
     * conunts how many contestants there is in the bench. One more important thing,
     * we need the ID of the last contestant that is up to then inform the coach that
     * all the contestants are ready to go.
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    public void waitForCallContestants(String team, int idC);
    
}
