/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.playground;

import java.rmi.RemoteException;
import structures.VectorTimestamp;

/**
 * Contestant interface of Playground instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * @param id contestant identifier
     * @param team "A" or "B"
     * @throws java.rmi.RemoteException
     */
    public void pullTheRope(int id, String team) throws RemoteException;
    
    /**
     * Wait for start trial. Contestant method.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp waitForStartTrial(VectorTimestamp vt) throws RemoteException;
    
    /**
     * Wait for assert trial decision. Contestant method.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp waitForAssertTrialDecision(VectorTimestamp vt) throws RemoteException;
    
}
