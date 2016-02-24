/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author
 */
public interface IContestant {
    
    /**
     * In Contestants life cycle, transition between "stand in position" and "doYourBest"
     */
    public void getReady();

    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     */
    public void pullTheRope();
}
