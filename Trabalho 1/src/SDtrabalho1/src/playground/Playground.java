/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Playground implements IReferee, IContestant{
    
    public Playground(){
    
    }
    
    /* REFEREE METHODS */
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion"
     */
    @Override
    public synchronized void startTrial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void waitForStartTrial(){
        
    }
    
    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion"
     */
    @Override
    public synchronized void assertTrialDecision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void waitForAssertTrialDecision(){
        
    }
    
    /* CONTESTANT METHODS */
    
    /**
     * In Contestants life cycle, transition between "stand in position" and "doYourBest"
     */
    @Override
    public void getReady() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * Random time interval in the simulation 
     */
    @Override
    public void pullTheRope() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
