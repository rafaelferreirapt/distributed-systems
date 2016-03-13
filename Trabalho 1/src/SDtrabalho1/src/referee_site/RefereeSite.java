/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSite implements ICoach, IContestant, IReferee{
    
    private boolean informRefereeA = false, informRefereeB = false;
    private int amDoneCounter = 0;
    private boolean matchEnds = false;
    public RefereeSite(){
    
    }
    
    /* REFEREE METHODS */
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a game"
    */
    @Override
    public void annouceNewGame() {
        
    }

    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game" 
    */
    @Override
    public void declareGameWinner() {
        
    }

    /**
    * In referee life cycle, transition between "end of a game" and "end of the match" 
    */
    @Override
    public void declareMatchWinner() {
        this.matchEnds = true;
    }
    
    /* COACH METHODS */
    
     /**
     *the referee is waken up by the last of the coaches in operation 
     *"informReferee" when the teams are ready to proceed
     */
    @Override
    public synchronized void informReferee(String team) {
        if(team.equals("A")){
            this.informRefereeA = true;
        }else if(team.equals("B")){
            this.informRefereeB = true;
        }
        notifyAll();

    }
    
    @Override
    public synchronized void waitForInformReferee(){
        while(!this.informRefereeA || !this.informRefereeB){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /* PLAYERS METHODS */
    
     /**
     *the referee is waken up by the last of the contestants in operation amDone
     *when the trial has come to an end
     */
    @Override
    public synchronized void amDone() {
        this.amDoneCounter++;
        if(this.amDoneCounter == 6){
            notifyAll();
        }
    }
    
    @Override
    public synchronized void waitForAmDone(){
        while(this.amDoneCounter != 6){
            try {
                wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public synchronized boolean endOfMatch(){
        return this.matchEnds;
    }
}
