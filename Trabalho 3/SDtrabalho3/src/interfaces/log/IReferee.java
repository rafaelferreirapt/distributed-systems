/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.RefereeState;

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
     * @throws java.rmi.RemoteException
     */
    public void newGame() throws RemoteException;
    
    /**
     *
     * @throws java.rmi.RemoteException
     */
    public void newTrial() throws RemoteException;
    
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
     * @throws java.rmi.RemoteException
     */
    public void printGameWinner() throws RemoteException;
    
    /**
     *
     * @param gameNumber
     * @throws java.rmi.RemoteException
     */
    public void newGame(int gameNumber) throws RemoteException;
    
    /**
     *
     * @param state
     * @throws java.rmi.RemoteException
     */
    public void setRefereeState(RefereeState state) throws RemoteException;
    
    /**
     *
     * @throws java.rmi.RemoteException
     */
    public void declareMatchWinner() throws RemoteException;
}
