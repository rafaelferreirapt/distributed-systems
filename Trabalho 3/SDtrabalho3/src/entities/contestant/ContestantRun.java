package entities.contestant;

import entities.referee.RefereeRun;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import interfaces.bench.BenchInterface;
import interfaces.log.LogInterface;
import interfaces.playground.PlaygroundInterface;
import interfaces.referee_site.RefereeSiteInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.Constants;
import structures.RegistryConfig;

/**
 * Contestant Run
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ContestantRun {
    
    private static int N_CONTESTANTS_TEAM;
    private static String[] teams;
    private static int nContestants;
    
    public static void main(String [] args) { 
        N_CONTESTANTS_TEAM = Constants.N_CONTESTANTS_TEAM;
        teams = Constants.teams;
        nContestants = N_CONTESTANTS_TEAM * 2;
        
        ArrayList<Contestant> contestants = new ArrayList<>(nContestants);
        
        BenchInterface bp = null;
        RefereeSiteInterface rsp = null;
        PlaygroundInterface pp = null;
        LogInterface log = null;
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        // port de escuta do serviço
        int rmiRegPortNumb;

        RegistryConfig rc = new RegistryConfig("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            rsp = (RefereeSiteInterface) registry.lookup (RegistryConfig.refereeSiteNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating referee site: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Referee site is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            pp = (PlaygroundInterface) registry.lookup (RegistryConfig.playgroundNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating playground: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Playground is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            log = (LogInterface) registry.lookup (RegistryConfig.logNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating Log: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Log is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            bp = (BenchInterface) registry.lookup (RegistryConfig.benchNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating bench: " + e.getMessage () + "!");
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Bench is not registered: " + e.getMessage () + "!");
            System.exit(1);
        }
        
        for (int i = 0; i < nContestants; i++){
            if(i < N_CONTESTANTS_TEAM){
                contestants.add(new Contestant(pp, bp, rsp, i+1, i, teams[0], log));
            }else{
                contestants.add(new Contestant(pp, bp, rsp, i-N_CONTESTANTS_TEAM+1, i, teams[1], log));
            }
        }
        
        System.out.println("Number of contestants: " + contestants.size());
        
        for (Contestant c : contestants)
            c.start();
        
        for (Contestant c : contestants) { 
            try { 
                c.join ();
            } catch (InterruptedException e) {}
        }
        
        System.out.println("Say to log that I have finished!");
        
        try {
            log.finished();
        } catch (RemoteException ex) {
            Logger.getLogger(RefereeRun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Done!");
    }
}
