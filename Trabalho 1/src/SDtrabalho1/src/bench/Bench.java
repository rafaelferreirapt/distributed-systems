/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

/**
 *
 * @author
 */
public class Bench implements IReferee, ICoach, IContestant{
    
    public Bench(){
    
    }
    
    /* REFEREE METHODS */
    
    /**
     * the coaches are waken up by the referee in operation callTrial to start 
     * selecting next team
     */
    @Override
    public synchronized void callTrial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * the coaches are waken up in operation assertTrialDecision by the referee
     * the contestants are made to sleep for a random time interval in the 
     * simulation they block next and are waken up in operation assertTrialDecision by the referee
     */
    @Override
    public synchronized void assertTrialDecision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* COACH METHODS */

    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command"
     */
    @Override
    public void reviewNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team"
     */
    @Override
    public synchronized void callContestants() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* PLAYERS METHODS */

    /**
     * the coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     */
    @Override
    public synchronized void followCoachAdvice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     */
    @Override
    public void seatDown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
