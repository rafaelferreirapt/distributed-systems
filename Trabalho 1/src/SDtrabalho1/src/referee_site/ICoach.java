/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface ICoach {
    
    /**
     * The referee is waken up by the last of the coaches in operation 
     * "informReferee" when the teams are ready to proceed
     * @param team
     */
    public void informReferee(String team);

    /**
     * End of the match.
     * @return
     */
    public boolean endOfMatch();
}
