/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import communication.message.Message;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxyWrapper;
import java.util.HashMap;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSettsProxy {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;
    
    public NodeSettsProxy(){
        String json_path = "hosts.json";
        
        if(NodeSetts.DEBUG){
            json_path = "debug_hosts.json";
        }
        
        NodeSetts nodeSetts = new NodeSetts(json_path);
        
        this.SERVER_HOST = NodeSetts.SERVER_HOSTS.get("NodeSetts");
        this.SERVER_PORT = NodeSetts.SERVER_PORTS.get("NodeSetts");
    }
    
    private WrapperMessage communicate(Message m){
        return ClientProxyWrapper.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    public HashMap<String, String> SERVER_HOSTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStrStrMap();
    }
    
    public HashMap<String, Integer> SERVER_PORTS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStrIntMap();
    }
    
    public int N_COACHS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    public int NUMBER_OF_TRIALS() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    public int NUMBER_OF_GAMES() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    public int N_CONTESTANTS_TEAM() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
    public int MAX_STRENGTH() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    public int MIN_STRENGTH() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    public String[] teams() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getStringArr();
    }
    public int DELAY_MIN() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    public int DELAY_MAX() {
        MessageType mt = MessageType.valueOf(new Object(){}.getClass().getEnclosingMethod().getName());
        WrapperMessage wp = communicate(new Message(mt));
        return wp.getMessage().getInteger();
    }
    
}
