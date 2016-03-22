/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 * Referee interface of Referee Site instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a game".
    */
    public void annouceNewGame();
    
    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game".
    */
    public void declareGameWinner();
    
    /**
    * In referee life cycle, transition between "end of a game" and "end of the match".
    */
    public void declareMatchWinner();
    
    /**
     * End of the match. 
     * @return if has endeed or not
     */
    public boolean endOfMatch();
    
    /**
     * The referee waits to be informed by the team A and B.
     */
    public void waitForInformReferee();

    /**
     * The referee will wait fot the last contestant in operation amDone
     * when the trial has come to an end.
     */
    public void waitForAmDone();

    /**
     * The referee waits for the contestant to be positioned.
     */
    public void waitAllPositioned();

}
