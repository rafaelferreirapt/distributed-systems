/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 * Contestant interface of Referee Site instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * The referee is waken up by the last of the contestants in operation amDone
     * when the trial has come to an end.
     */
    public void amDone();

    /**
     * End of the match. 
     * @return
     */
    public boolean endOfMatch();
    
    /**
     * The contestant notify the referee that is positioned.
     */
    public void positioned();
    

}
