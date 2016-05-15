/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.bench;

import java.rmi.RemoteException;
import structures.VectorTimestamp;

/**
 * Referee interface of Bench instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
     * The referee must wait that all the coaches are waiting for the call trial
     * and all the contestants are in the bench, then the referee can continue 
     * the trial, it will reset all the flags, the flag to the contestants selected
     * in the Team A or B and set the coaches wait for call trial to zero. The
     * coachs are sleeping and need the flag callTrialTaken true to procede. In the
     * final there is a notifyAll() to wake up all the entities in the Bench.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp callTrial(VectorTimestamp vt) throws RemoteException;

    /**
     * The referee will waken up the coaches with the trialDecisionTaken = true and 
     * with the notifyAll().
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp assertTrialDecision(VectorTimestamp vt) throws RemoteException;
    
    /**
     * This method will wakeup all the entities in the bench and declare that
     * the match was ended.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp wakeUp(VectorTimestamp vt) throws RemoteException;
}
