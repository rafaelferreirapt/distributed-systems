/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.playground;

import java.rmi.RemoteException;

/**
 * Referee interface of Playground instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion".
     * @throws java.rmi.RemoteException
     */
    public void startTrial() throws RemoteException;
    
    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion".
     * @throws java.rmi.RemoteException
     */
    public void assertTrialDecision() throws RemoteException;
    
}
