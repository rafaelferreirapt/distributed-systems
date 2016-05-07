/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package playground;

import interfaces.RegisterInterface;
import interfaces.playground.PlaygroundInterface;
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

/**
 * Playground instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Playground implements PlaygroundInterface{
    
    private static boolean trialDecisionTaken = false, startTrialTaken = false;
    private int contestantsIn = 0;
    private int contestantsAlerted = 0;
    
    private final int DELAY_MAX = Constants.DELAY_MAX;
    private final int DELAY_MIN = Constants.DELAY_MIN;
    
    private final interfaces.log.IPlayground log;
    
    /**
     * Log is a singleton, it needs the getInstance method.
     * @param l
     */
    public Playground(interfaces.log.IPlayground l){
        this.log = l;
    }
    
    /**
     * In Referee life cycle, transition between "teams ready" and "wait for trial conclusion".
     */
    @Override
    public synchronized void startTrial() {
        startTrialTaken = true;
        notifyAll();
    }

    /**
     * Wait for start trial. Contestant method.
     */
    @Override
    public synchronized void waitForStartTrial(){
        while(!startTrialTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(++this.contestantsIn == 6){
            this.contestantsIn = 0;
            startTrialTaken = false;
        }
    }
    
    /**
     * In Referee life cycle, transition between "wait for trial conclusion" and "wait for trial conclusion".
     */
    @Override
    public synchronized void assertTrialDecision() {
        trialDecisionTaken = true;
        notifyAll();
    }
    
    /**
     * Wait for assert trial decision. Contestant method.
     */
    @Override
    public synchronized void waitForAssertTrialDecision(){
        while(!trialDecisionTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(++this.contestantsAlerted == 6){
            trialDecisionTaken = false;
            this.contestantsAlerted = 0;
        }
    }
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "doYourBest"
     * Random time interval in the simulation 
     * @param id contestant identifier
     * @param team "A" or "B"
     */
    @Override
    public void pullTheRope(int id, String team) {
        try {
            Thread.sleep((int)Math.ceil(Math.random() * DELAY_MAX + DELAY_MIN));
        } catch (InterruptedException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            log.updateRope(team, id);
        } catch (RemoteException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.playgroundNameEntry;

        
        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Playground registration exception: " + e.getMessage());
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Playground not bound exception: " + e.getMessage());
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Playground.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Playground closed.");
    }
    
}
