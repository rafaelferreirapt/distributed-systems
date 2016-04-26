/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxy;
import java.util.HashMap;

/**
 * Proxy for all clients get the configuration that they need
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSettsProxy {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;

    /**
     * Node settings, if is in mode DEBUG it will launch in localhost
     */
    public NodeSettsProxy(){
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        NodeSetts nodeSetts = new NodeSetts(json_path);
        
        this.SERVER_HOST = NodeSetts.SERVER_HOSTS.get("NodeSetts");
        this.SERVER_PORT = NodeSetts.SERVER_PORTS.get("NodeSetts");
    }
    
    /**
    * Communicate method to communicate with the Node Setts server
    */
    private WrapperMessage communicate(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
    * SERVER HOSTS
    * @return 
    */
    public HashMap<String, String> SERVER_HOSTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStrStrMap();
    }
    
    /**
    * SERVER PORTS
    * @return 
    */
    public HashMap<String, Integer> SERVER_PORTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStrIntMap();
    }
    
    /**
    * Number of coachs
    * @return 
    */
    public int N_COACHS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Number of trials
    * @return 
    */
    public int NUMBER_OF_TRIALS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Number of games
    * @return 
    */
    public int NUMBER_OF_GAMES() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Number of contestants by team
    * @return 
    */
    public int N_CONTESTANTS_TEAM() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Max strength
    * @return 
    */
    public int MAX_STRENGTH() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Min strength
    * @return 
    */
    public int MIN_STRENGTH() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Teams
    * @return 
    */
    public String[] teams() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStringArr();
    }
    
    /**
    * Delay min
    * @return 
    */
    public int DELAY_MIN() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    /**
    * Delay max
    * @return 
    */
    public int DELAY_MAX() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
}
