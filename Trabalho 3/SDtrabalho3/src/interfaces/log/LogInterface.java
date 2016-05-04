/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.log;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public interface LogInterface extends ICoach, IContestant, IReferee, IPlayground, Remote{
    
    /**
     * This function is used for the logging to signal the shop to shutdown.
     * 
     * @throws RemoteException may throw during a execution of a remote method call
     */
    public void signalShutdown() throws RemoteException;
}
