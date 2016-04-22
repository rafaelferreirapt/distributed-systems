package bench;

import communication.ServerChannel;
import communication.proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSettsProxy;

public class BenchRun {
    
    private static int SERVER_PORT;
    private static int N_CONTESTANTS_TEAM;
    
    public static void main(String[] args) throws SocketException {
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        SERVER_PORT = proxy.SERVER_PORTS().get("bench");
        N_CONTESTANTS_TEAM = proxy.N_CONTESTANTS_TEAM();
        
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(SERVER_PORT);    
        schan.start();
        
        BenchServer benchServer = new BenchServer(N_CONTESTANTS_TEAM, N_CONTESTANTS_TEAM);
        System.out.println("Bench service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, benchServer);
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(BenchRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
