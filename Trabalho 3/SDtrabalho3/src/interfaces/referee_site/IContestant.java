/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package interfaces.referee_site;

import java.rmi.RemoteException;
import structures.VectorTimestamp;

/**
 * Contestant interface of Referee Site instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface IContestant {
    
    /**
     * The referee is waken up by the last of the contestants in operation amDone
     * when the trial has come to an end.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp amDone(VectorTimestamp vt) throws RemoteException;

    /**
     * End of the match. 
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean endOfMatch() throws RemoteException;
    
    /**
     * The contestant notify the referee that is positioned.
     * @param vt
     * @return 
     * @throws java.rmi.RemoteException
     */
    public VectorTimestamp positioned(VectorTimestamp vt) throws RemoteException;
    

}
