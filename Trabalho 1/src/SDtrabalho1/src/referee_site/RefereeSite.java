/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSite implements ICoach, IContestant, IReferee{
    
    public RefereeSite(){
    
    }
    
    /* REFEREE METHODS */
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a game"
    */
    @Override
    public void annouceNewGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game" 
    */
    @Override
    public void declareGameWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
    * In referee life cycle, transition between "end of a game" and "end of the match" 
    */
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
    
    @Override
    public synchronized void waitForInformReferee(){
        
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
    
    @Override
    public synchronized void waitForAmDone(){
        
    }
    
    @Override
    public synchronized boolean endOfMatch(){
        return false;
    }
}
