/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author
 */
public interface ICoach {
    
    /* COACH METHODS */

    /**
     *the referee is waken up by the last of the coaches in operation 
     *"informReferee" when the teams are ready to proceed
     */
    public void informReferee();
}
