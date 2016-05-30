/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package referee_site;

import interfaces.RegisterInterface;
import interfaces.referee_site.RefereeSiteInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.Constants;
import structures.RegistryConfig;
import structures.VectorTimestamp;

/**
 * Referee Site instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSite implements RefereeSiteInterface{
    
    private boolean informRefereeA = false, informRefereeB = false;
    private int amDoneCounter = 0, positionedCounter = 0;
    private boolean matchEnds = false, amDoneCondition = false, positionedCondition = false;
    
    private final VectorTimestamp clocks;

    public RefereeSite(){
        this.clocks = new VectorTimestamp(Constants.N_COACHS + Constants.N_CONTESTANTS_TEAM*2 + 2, 0);
    }
    
    /**
    * In referee life cycle, transition between "start of the match" and "start of a game" or 
    * between "end of a game" and "start of a gam   e".
     * @param vt
     * @return 
    */
    @Override
    public synchronized VectorTimestamp annouceNewGame(VectorTimestamp vt) {
        this.clocks.update(vt);
        return this.clocks.clone();
    }

    /**
    * In referee life cycle, transition between "wait for trial conclusion" and "end of a game".
    */
    @Override
    public synchronized VectorTimestamp declareGameWinner(VectorTimestamp vt) {
        this.clocks.update(vt);
        return this.clocks.clone();
    }

    /**
    * In referee life cycle, transition between "end of a game" and "end of the match".
    */
    @Override
    public synchronized VectorTimestamp declareMatchWinner(VectorTimestamp vt) {
        this.clocks.update(vt);
        this.matchEnds = true;
        return this.clocks.clone();        
    }
    
     /**
     * The referee is waken up by the last of the coaches in operation 
     * "informReferee" when the teams are ready to proceed. Coach method.
     * @param team "A" or "B"
     */
    @Override
    public synchronized VectorTimestamp informReferee(String team, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        if(team.equals("A")){
            this.informRefereeA = true;
        }else if(team.equals("B")){
            this.informRefereeB = true;
        }
        notifyAll();
        return this.clocks.clone();
    }
    
    /**
     * The referee waits to be informed by the team A and B.
     */
    @Override
    public synchronized VectorTimestamp waitForInformReferee(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        while(!this.informRefereeA || !this.informRefereeB){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.informRefereeA = false;
        this.informRefereeB = false;
        return this.clocks.clone();
    }
    
     /**
     * The referee is waken up by the last of the contestants in operation amDone
     * when the trial has come to an end.
     */
    @Override
    public synchronized VectorTimestamp amDone(VectorTimestamp vt) throws RemoteException {
        this.clocks.update(vt);
        if(++this.amDoneCounter == 6){
            this.amDoneCondition = true;
            notifyAll();
        }
        return this.clocks.clone();
    }
    
    /**
     * The referee will wait fot the last contestant in operation amDone
     * when the trial has come to an end.
     */
    @Override
    public synchronized VectorTimestamp waitForAmDone(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        while(!this.amDoneCondition){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.amDoneCounter = 0;
        this.amDoneCondition = false;
        return this.clocks.clone();
    }
    
    /**
     * The contestant notify the referee that is positioned.
     */
    @Override
    public synchronized VectorTimestamp positioned(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        if(++this.positionedCounter == 6){
            this.positionedCondition = true;
            notifyAll();
        }
        return this.clocks.clone();
    }
    
    /**
     * The referee waits for the contestant to be positioned.
     */
    @Override
    public synchronized VectorTimestamp waitAllPositioned(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        while(!this.positionedCondition){
            try {
                wait();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.positionedCounter = 0;
        this.positionedCondition = false;
        return this.clocks.clone();
    }    
    
    /**
     * End of the match.
     * @return if has endeed or not.
     */
    @Override
    public synchronized boolean endOfMatch() throws RemoteException{
        return this.matchEnds;
    }

    @Override
    public void signalShutdown() throws RemoteException {
        RegisterInterface reg = null;
        Registry registry = null;

        String rmiRegHostName;
        int rmiRegPortNumb;
        
        RegistryConfig rc = new RegistryConfig("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.refereeSiteNameEntry;
        
        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Referee Site registration exception: " + e.getMessage());
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Referee Site not bound exception: " + e.getMessage());
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(RefereeSite.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Referee Site closed.");
    }
}
