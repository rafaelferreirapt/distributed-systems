package playground;

import communication.ServerChannel;
import communication.proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSetts;

public class PlaygroundRun {
    
    public static void main(String[] args) throws SocketException {
        LogProxy log = new LogProxy();

        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(NodeSetts.SERVER_PORTS.get("playground"));    
        schan.start();
        
        PlaygroundServer playgroundServer = new PlaygroundServer(log);
        System.out.println("Playground service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, playgroundServer);
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(PlaygroundRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
