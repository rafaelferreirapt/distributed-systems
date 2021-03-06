/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.bench;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface BenchInterface extends ICoach, IContestant, IReferee, Remote {
    /**
     * This function is used for the log to signal the referee site to shutdown.
     * 
     * @throws RemoteException may throw during a execution of a remote method call
     */
    public void signalShutdown() throws RemoteException;
    
}
