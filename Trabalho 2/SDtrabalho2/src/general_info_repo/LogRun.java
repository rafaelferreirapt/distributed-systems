package general_info_repo;

import communication.ServerChannel;
import communication.proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSettsProxy;

public class LogRun {
    
    private static int SERVER_PORT;
    /**
     * This class will launch one server listening one port and processing
     * the events.
     * @param args
     * @throws java.net.SocketException
     */
    public static void main(String[] args) throws SocketException {
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_PORT = proxy.SERVER_PORTS().get("Log");
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(SERVER_PORT);    
        schan.start();
        
        LogServer logServer = new LogServer();
        System.out.println("Log service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, logServer);
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(LogRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
