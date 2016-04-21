package referee_site;

import communication.ServerChannel;
import communication.proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSetts;

public class RefereeSiteRun {
    
    public static void main(String[] args) throws SocketException {
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(NodeSetts.SERVER_PORTS.get("refereeSite"));    
        schan.start();
        
        RefereeSiteServer refereeSiteServer = new RefereeSiteServer();
        System.out.println("Referee Site service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, refereeSiteServer);
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(RefereeSiteRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
