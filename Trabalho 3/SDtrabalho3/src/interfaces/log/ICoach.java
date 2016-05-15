/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.CoachState;
import structures.VectorTimestamp;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface ICoach {
    
    /**
     *
     * @param team
     * @param state
     * @throws java.rmi.RemoteException
     */
    public void initCoachState(String team, CoachState state) throws RemoteException;
    
    /**
     *
     * @param team
     * @throws java.rmi.RemoteException
     */
    public void refreshStrengths(String team) throws RemoteException;
    
    /**
     *
     * @param team
     * @param state
     * @param vt
     * @throws java.rmi.RemoteException
     */
    public void setCoachState(String team, CoachState state, VectorTimestamp vt) throws RemoteException;
}
