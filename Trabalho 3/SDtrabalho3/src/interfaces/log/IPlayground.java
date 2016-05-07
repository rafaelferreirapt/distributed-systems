/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IPlayground {

    /**
     *
     * @param team
     * @param contestant
     * @throws java.rmi.RemoteException
     */
    public void updateRope(String team, int contestant) throws RemoteException;
}
