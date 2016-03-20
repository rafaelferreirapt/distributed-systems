/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

import general_info_repo.Log;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Playground implements IReferee, IContestant{
    
    private final Log log;
    private static boolean trialDecisionTaken = false, startTrialTaken = false;
    private int contestantsIn = 0;
    private int contestantsAlerted = 0;
    
    public Playground(){
        this.log = Log.getInstance();
    }
    
    /**
     *
     */
    /* REFEREE METHODS */
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion"
     */
    @Override
    public synchronized void startTrial() {
        startTrialTaken = true;
        notifyAll();
    }

    @Override
    public synchronized void waitForStartTrial(){
        while(!startTrialTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(++this.contestantsIn == 6){
            this.contestantsIn = 0;
            startTrialTaken = false;
        }
    }
    
    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion"
     */
    @Override
    public synchronized void assertTrialDecision() {
        trialDecisionTaken = true;
        notifyAll();
    }
    
    @Override
    public synchronized void waitForAssertTrialDecision(){
        while(!trialDecisionTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(++this.contestantsAlerted == 6){
            trialDecisionTaken = false;
            this.contestantsAlerted = 0;
        }
    }
    
    /* CONTESTANT METHODS */
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * Random time interval in the simulation 
     */
    @Override
    public void pullTheRope(int strength, String team) {
        /*
        // meter configurável
        try {
            Thread.sleep((int)Math.ceil(Math.random() * 2000 + 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        log.updateRope(team, strength);
    }
    
}
