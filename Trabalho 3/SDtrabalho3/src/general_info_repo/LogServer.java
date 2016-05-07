package general_info_repo;

import interfaces.log.LogInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.RegisterInterface;
import structures.RegistryConfig;

/**
 * Server that extends the Log and will process the events
 * of the server.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class LogServer {

    /**
     * The main class for the log server.
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
        
        /* instanciação do objecto remoto que representa o log e geração de um stub para ele */
        Log log = null;
        LogInterface logInterface = null;
        log = new Log("");
        
        try {
            logInterface = (LogInterface) UnicastRemoteObject.exportObject(log, rc.loggingPort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o log: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O stub para o log foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.logNameEntry;
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
            reg.bind(nameEntryObject, logInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do log: " + e.getMessage());
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O log já está registado: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("O log foi registado!");
    }
    
}
