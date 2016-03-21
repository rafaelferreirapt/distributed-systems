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
    private int amDoneCounter = 0, positionedCounter = 0;
    private boolean matchEnds = false, amDoneCondition = false, positionedCondition = false;
    public RefereeSite(){
    
    }
    
    /**
     *
     */
    
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
        this.informRefereeA = false;
        this.informRefereeB = false;
    }
    
    /* PLAYERS METHODS */
    
     /**
     *the referee is waken up by the last of the contestants in operation amDone
     *when the trial has come to an end
     */
    @Override
    public synchronized void amDone() {
        if(++this.amDoneCounter == 6){
            this.amDoneCondition = true;
            notifyAll();
        }
    }
    
    @Override
    public synchronized void waitForAmDone(){
        while(!this.amDoneCondition){
            try {
                wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.amDoneCounter = 0;
        this.amDoneCondition = false;
    }
    
    @Override
    public synchronized void positioned() {
        if(++this.positionedCounter == 6){
            this.positionedCondition = true;
            notifyAll();
        }
    }
    
    @Override
    public synchronized void waitAllPositioned() {
        while(!this.positionedCondition){
            try {
                wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.positionedCounter = 0;
        this.positionedCondition = false;
    }    
    
    @Override
    public synchronized boolean endOfMatch(){
        return this.matchEnds;
    }
}
