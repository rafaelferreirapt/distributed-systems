package settings;

import communication.ServerChannel;
import communication.proxy.ServerProxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NodeSettsRun {
    
    public static void main(String[] args) throws SocketException {
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        if(args.length == 1){
            json_path = args[0];
        }
        
        // canais de comunicação
        ServerChannel schan, schani;
        
        // thread agente prestador do serviço
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        
        NodeSettsServer nodeSettsServer = new NodeSettsServer(json_path);
        
        // criação do canal de escuta e sua associação
        schan = new ServerChannel(nodeSettsServer.SERVER_PORTS.get("NodeSetts"));    
        schan.start();
        
        System.out.println("Node Setts service has started!\nServer is listening.");

        /* processamento de pedidos */
        
        while (true) {
            
            try {
                // entrada em processo de escuta
                schani = schan.accept();
                // lançamento do agente prestador do serviço
                cliProxy = new ServerProxy(schan, schani, nodeSettsServer);
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                Logger.getLogger(NodeSettsRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
