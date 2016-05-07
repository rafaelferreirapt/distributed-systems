/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.ContestantState;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     *
     * @param state
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void initContestant(ContestantState state, String team, int contestant) throws RemoteException;

    /**
     *
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void setContestantLastTrial(String team, int contestant) throws RemoteException;
    
    /**
     *
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void removePosition(String team, int contestant) throws RemoteException;
    
    /**
     *
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void setPosition(String team, int contestant) throws RemoteException;
    
    /**
     *
     * @param state
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void setContestantState(ContestantState state, String team, int contestant) throws RemoteException;
}
