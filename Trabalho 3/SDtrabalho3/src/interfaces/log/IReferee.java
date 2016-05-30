/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.RefereeState;
import structures.VectorTimestamp;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IReferee {
    
    /**
     *
     * @param state
     * @throws java.rmi.RemoteException
     */
    public void initRefereeState(RefereeState state) throws RemoteException;
    
    /**
     *
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp newGame(VectorTimestamp vt) throws RemoteException;
    
    /**
     *
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp newTrial(VectorTimestamp vt) throws RemoteException;
    
    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public int assertTrialDecision() throws RemoteException;
    
    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public int getNumberOfGames() throws RemoteException;
    
    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public int getTotalNumberOfGames() throws RemoteException;
    
    /**
     *
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp printGameWinner(VectorTimestamp vt) throws RemoteException;
    
    /**
     *
     * @param gameNumber
     * @throws java.rmi.RemoteException
     */
    public void newGame(int gameNumber) throws RemoteException;
    
    /**
     *
     * @param state
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp setRefereeState(RefereeState state, VectorTimestamp vt) throws RemoteException;
    
    /**
     *
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp declareMatchWinner(VectorTimestamp vt) throws RemoteException;
}
