/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Playground implements IReferee, IContestant{
    
    private static boolean trialDecisionTaken = false, startTrialTaken = false;
    private int trialState = 0;
    private int contestantsIn = 0;
    private int contestantsAlerted = 0;

    public Playground(){
    
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
        this.trialState = 0;

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
        this.contestantsIn++;
        if(this.contestantsIn == 6){
            startTrialTaken = false;
            this.contestantsIn = 0;
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
    public int getTrialState(){
        return this.trialState;
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
        if(team.equals("A")){
            trialState -= strength;
        }else if(team.equals("B")){
            trialState += strength;
        }
        
    }
    
}
