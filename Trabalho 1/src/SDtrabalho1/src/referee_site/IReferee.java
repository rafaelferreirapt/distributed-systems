/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author
 */
public interface IReferee {
    
    /* REFEREE METHODS */
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a game"
    */
    public void annouceNewGame();
    
    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game" 
    */
    public void declareGameWinner();
    
    /**
    * In referee life cycle, transition between "end of a game" and "end of the match" 
    */
    public void declareMatchWinner();

}
