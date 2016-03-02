/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /* CONTESTANTS METHODS */
    
    /**
     *the referee is waken up by the last of the contestants in operation amDone
     *when the trial has come to an end
     */
    public void amDone();
}
