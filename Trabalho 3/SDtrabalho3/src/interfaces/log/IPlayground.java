/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.log;

import java.rmi.RemoteException;
import structures.VectorTimestamp;

/**
 * 
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IPlayground {

    /**
     *
     * @param team
     * @param contestant
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp updateRope(String team, int contestant, VectorTimestamp vt) throws RemoteException;
}
