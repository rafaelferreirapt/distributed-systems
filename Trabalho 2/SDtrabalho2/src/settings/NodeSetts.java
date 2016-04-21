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
    
    public static final HashMap<String, Integer> SERVER_PORTS;
    public static final HashMap<String, String> SERVER_HOSTS;
    
    public static final int N_COACHS = 2;
    public static final int NUMBER_OF_TRIALS = 6;
    public static final int NUMBER_OF_GAMES = 3;
    public static final int N_CONTESTANTS_TEAM = 5; 
    public static final int MAX_STRENGTH = 24;
    public static final int MIN_STRENGTH = 20;  
    
    public static String teams[] = {"A", "B"};
    
    public static final int DELAY_MIN = 0;
    public static final int DELAY_MAX = 0;
    
    static{
        // 22120 - 22129
        SERVER_PORTS = new HashMap<>();
        SERVER_PORTS.put("log", 22123);
        SERVER_PORTS.put("bench", 22122);
        SERVER_PORTS.put("playground", 22121);
        SERVER_PORTS.put("refereeSite", 22120);
        
        SERVER_HOSTS = new HashMap<>();
        SERVER_HOSTS.put("log", "127.0.0.1");
        SERVER_HOSTS.put("bench", "127.0.0.1");
        SERVER_HOSTS.put("playground", "127.0.0.1");
        SERVER_HOSTS.put("refereeSite", "127.0.0.1");
    }
}
