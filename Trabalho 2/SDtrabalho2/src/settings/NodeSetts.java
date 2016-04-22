/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import java.util.HashMap;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSetts {
    
    protected static final HashMap<String, Integer> SERVER_PORTS;
    protected static final HashMap<String, String> SERVER_HOSTS;
    
    protected static final int N_COACHS = 2;
    protected static final int NUMBER_OF_TRIALS = 6;
    protected static final int NUMBER_OF_GAMES = 3;
    protected static final int N_CONTESTANTS_TEAM = 5; 
    protected static final int MAX_STRENGTH = 24;
    protected static final int MIN_STRENGTH = 20;  
    
    protected static String teams[] = {"A", "B"};
    
    protected static final int DELAY_MIN = 0;
    protected static final int DELAY_MAX = 0;
    
    static{
        // 22120 - 22129
        SERVER_PORTS = new HashMap<>();
        SERVER_PORTS.put("nodeSetts", 22124);
        SERVER_PORTS.put("log", 22123);
        SERVER_PORTS.put("bench", 22122);
        SERVER_PORTS.put("playground", 22121);
        SERVER_PORTS.put("refereeSite", 22120);
        
        SERVER_HOSTS = new HashMap<>();
        SERVER_HOSTS.put("nodeSetts", "127.0.0.1");
        SERVER_HOSTS.put("log", "127.0.0.1");
        SERVER_HOSTS.put("bench", "127.0.0.1");
        SERVER_HOSTS.put("playground", "127.0.0.1");
        SERVER_HOSTS.put("refereeSite", "127.0.0.1");
    }
}
