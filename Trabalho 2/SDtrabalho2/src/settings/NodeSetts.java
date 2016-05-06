/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;

/**
 * Node settings, all the setting in one place
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class NodeSetts {
    
    protected static HashMap<String, Integer> SERVER_PORTS;
    protected static HashMap<String, String> SERVER_HOSTS;
    
    protected static boolean DEBUG = false;
    
    protected final int N_COACHS = 2;
    protected final int NUMBER_OF_TRIALS = 6;
    protected final int NUMBER_OF_GAMES = 10;
    protected final int N_CONTESTANTS_TEAM = 25;
    protected final int MAX_STRENGTH = 24;
    protected final int MIN_STRENGTH = 20;
    
    protected String teams[] = {"A", "B"};
    
    protected final int DELAY_MIN = 0;
    protected final int DELAY_MAX = 0;

    /**
     * Node settings, it will read from the jsonfilepath the JSON with the hosts
     * @param jsonfilepath
     */
    public NodeSetts(String jsonfilepath){
        JSONParser parser = new JSONParser();
        
        try {     
            Object obj = parser.parse(new FileReader(jsonfilepath));

            JSONArray json =  (JSONArray) obj;
            
            SERVER_HOSTS = JSONUtils.jsonToHashString(json);
        } catch (JSONException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | org.json.simple.parser.ParseException ex) {
            Logger.getLogger(NodeSetts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // 22120 - 22129
        SERVER_PORTS = new HashMap<>();
        SERVER_PORTS.put("NodeSetts", 22124);
        SERVER_PORTS.put("Log", 22123);
        SERVER_PORTS.put("Bench", 22122);
        SERVER_PORTS.put("Playground", 22121);
        SERVER_PORTS.put("RefereeSite", 22120);
    }
}
