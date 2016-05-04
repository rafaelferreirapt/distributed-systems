/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bench;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.bench.BenchInterface;
import interfaces.RegisterInterface;
import structures.RegistryConfig;
import structures.NodeSetts;

/**
 * Server that extends the Bench and will process the events
 * of the server.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class BenchServer {
    
    /**
     * The main class for the bench server.
     * @param args No arguments are going to be used.
     */
    public static void main(String[] args) {
        /* obtenção da localização do serviço de registo RMI */
        
        // nome do sistema onde está localizado o serviço de registos RMI
        String rmiRegHostName;
        
        // port de escuta do serviço
        int rmiRegPortNumb;            

        RegistryConfig rc = new RegistryConfig("../../config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        /* instanciação e instalação do gestor de segurança */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        /* instanciação do objecto remoto que representa o bench e geração de um stub para ele */
        Bench bench = null;
        BenchInterface benchInterface = null;
        bench = new Bench(NodeSetts.N_CONTESTANTS_TEAM, NodeSetts.N_CONTESTANTS_TEAM);
        
        try {
            benchInterface = (BenchInterface) UnicastRemoteObject.exportObject(bench, rc.benchPort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o bench: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o bench foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.benchNameEntry;
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
            reg.bind(nameEntryObject, benchInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do bench: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O bench já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O bench foi registado!");
    }
}
