/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.referee;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import interfaces.bench.BenchInterface;
import interfaces.log.LogInterface;
import interfaces.playground.PlaygroundInterface;
import interfaces.referee_site.RefereeSiteInterface;
import structures.RegistryConfig;

/**
 * Referee Run
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeRun {
    
    public static void main(String [] args) {
        BenchInterface bp = null;
        RefereeSiteInterface rsp = null;
        PlaygroundInterface pp = null;
        LogInterface log = null;
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        // port de escuta do serviço
        int rmiRegPortNumb;
        
        RegistryConfig rc = new RegistryConfig("../../config.ini");
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
        
        Referee ref = new Referee(pp, bp, rsp, log);
        
        System.out.println("Number of referees: 1");
       
        ref.start();
        
        try { 
            ref.join ();
        } catch (InterruptedException e) {}
        
        System.out.println("Done!");
    }
}
