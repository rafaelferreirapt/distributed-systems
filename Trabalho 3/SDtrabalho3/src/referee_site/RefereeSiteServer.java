/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package referee_site;

import interfaces.referee_site.RefereeSiteInterface;
import structures.RegistryConfig;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.RegisterInterface;

/**
 * Server that extends the RefereeSite and will process the events
 * of the server.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSiteServer{
    
    /**
     * The main class for the shop server.
     * @param args No arguments are going to be used.
     */
    public static void main(String[] args) {
        /* obtenção da localização do serviço de registo RMI */
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        
        // port de escuta do serviço
        int rmiRegPortNumb;            

        RegistryConfig rc = new RegistryConfig("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        /* instanciação e instalação do gestor de segurança */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        /* instanciação do objecto remoto que representa o referee site e geração de um stub para ele */
        RefereeSite referee_site = null;
        RefereeSiteInterface refereeSiteInterface = null;
        referee_site = new RefereeSite();
        
        try {
            refereeSiteInterface = (RefereeSiteInterface) UnicastRemoteObject.exportObject(referee_site, rc.refereeSitePort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o referee site: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o referee site foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.refereeSiteNameEntry;
        Registry registry = null;
        RegisterInterface reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O registo RMI foi criado!");

        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        }

        try {
            reg.bind(nameEntryObject, refereeSiteInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do referee site: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O referee site já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O referee site foi registado!");
    }
}
