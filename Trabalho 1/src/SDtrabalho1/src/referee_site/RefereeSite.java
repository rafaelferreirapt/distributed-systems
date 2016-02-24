/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author
 */
public class RefereeSite implements ICoach, IContestant, IReferee{
    
    public RefereeSite(){
    
    }
    
    /* REFEREE METHODS */
    
    @Override
    public void annouceNewGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void declareGameWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void declareMatchWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* COACH METHODS */
    
     /**
     *the referee is waken up by the last of the coaches in operation 
     *"informReferee" when the teams are ready to proceed
     */
    @Override
    public synchronized void informReferee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* PLAYERS METHODS */
    
     /**
     *the referee is waken up by the last of the contestants in operation amDone
     *when the trial has come to an end
     */
    @Override
    public synchronized void amDone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
