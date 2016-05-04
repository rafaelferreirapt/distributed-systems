package entities.coach;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import interfaces.bench.BenchInterface;
import interfaces.log.LogInterface;
import interfaces.referee_site.RefereeSiteInterface;
import structures.Constants;
import structures.RegistryConfig;

/**
 * Coach Run
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class CoachRun {
    
    private static int N_COACHS;
    private static String[] teams;
    
    /**
     * This class will launch coachs
     * the events.
     * @param args
     */
    public static void main(String [] args) {    
        N_COACHS = Constants.N_COACHS;
        teams = Constants.teams;
        
        ArrayList<Coach> coachs = new ArrayList<>(N_COACHS);
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        // port de escuta do serviço
        int rmiRegPortNumb;

        RegistryConfig rc = new RegistryConfig("../../config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        BenchInterface bi = null;
        RefereeSiteInterface rsi = null;
        LogInterface li = null;
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            li = (LogInterface) registry.lookup (RegistryConfig.logNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating log: " + e.getMessage () + "!");
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
            bi = (BenchInterface) registry.lookup (RegistryConfig.benchNameEntry);
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
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            rsi = (RefereeSiteInterface) registry.lookup (RegistryConfig.refereeSiteNameEntry);
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
        
        for (int i = 0; i < N_COACHS; i++){
            coachs.add(new Coach(bi, rsi, teams[i], li));
        }
        
        System.out.println("Number of coachs: " + coachs.size());
        
        for (Coach c : coachs)
            c.start();
        
        for (Coach c : coachs) { 
            try { 
                c.join ();
            } catch (InterruptedException e) {}
        }
        
        System.out.println("Done!");
    }
}
