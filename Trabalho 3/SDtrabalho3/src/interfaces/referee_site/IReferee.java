/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.referee_site;

import java.rmi.RemoteException;
import structures.VectorTimestamp;

/**
 * Referee interface of Referee Site instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a game".
     * @throws java.rmi.RemoteException
    */
    public void annouceNewGame() throws RemoteException;
    
    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game".
     * @throws java.rmi.RemoteException
    */
    public void declareGameWinner() throws RemoteException;
    
    /**
    * In referee life cycle, transition between "end of a game" and "end of the match".
     * @throws java.rmi.RemoteException
    */
    public void declareMatchWinner() throws RemoteException;
    
    /**
     * End of the match. 
     * @return if has endeed or not
     * @throws java.rmi.RemoteException
     */
    public boolean endOfMatch() throws RemoteException;
    
    /**
     * The referee waits to be informed by the team A and B.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp waitForInformReferee(VectorTimestamp vt) throws RemoteException;

    /**
     * The referee will wait fot the last contestant in operation amDone
     * when the trial has come to an end.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp waitForAmDone(VectorTimestamp vt) throws RemoteException;

    /**
     * The referee waits for the contestant to be positioned.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp waitAllPositioned(VectorTimestamp vt) throws RemoteException;

}
