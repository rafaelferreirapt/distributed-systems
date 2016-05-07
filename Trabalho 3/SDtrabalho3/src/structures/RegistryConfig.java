/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all the constants related with the Registry.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RegistryConfig {
    /**
     * Logging name entry on the registry.
     */
    public static String logNameEntry = "LoggingInt";

    /**
     * Bench name entry on the registry.
     */
    public static String benchNameEntry = "BenchInt";

    /**
     * Referee Site name entry on the registry.
     */
    public static String refereeSiteNameEntry = "refereeSiteInt";

    /**
     * Playground name entry on the registry.
     */
    public static String playgroundNameEntry = "PlaygroundInt";

    /**
     * RegisterHandler name entry on the registry.
     */
    public static String registerHandler = "RegisterHandler";
    
    /**
     * Bash property of the file.
     */
    private Properties prop;

    /**
     * Constructor that receives the file with the configurations.
     * @param filename path for the configuration file
     */
    public RegistryConfig(String filename) {
        prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
            prop.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(RegistryConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(RegistryConfig.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
    }
    
    /** 
     * Loads the parameter REGISTER_HOST from the configuration file.
     * @return parameter value
     */
    public String registryHost() {
        return prop.getProperty("registry_host");
    }
    
    /** 
     * Loads the parameter REGISTER_PORT from the configuration file.
     * @return parameter value
     */
    public int registryPort() {
        return Integer.parseInt(prop.getProperty("registry_port"));
    }
    /** 
     * Loads the parameter REGISTER_OBJECT_PORT from the configuration file.
     * @return parameter value
     */
    public int objectPort() {
        return Integer.parseInt(prop.getProperty("registryobject"));
    }
    /** 
     * Loads the parameter LOGGING_PORT from the configuration file.
     * @return parameter value
     */
    public int loggingPort() {
        return Integer.parseInt(prop.getProperty("log_port"));
    }
    /** 
     * Loads the parameter BENCH_PORT from the configuration file.
     * @return parameter value
     */
    public int benchPort() {
        return Integer.parseInt(prop.getProperty("bench_port"));
    }
    /** 
     * Loads the parameter PLAYGROUND_PORT from the configuration file.
     * @return parameter value
     */
    public int playgroundPort() {
        return Integer.parseInt(prop.getProperty("playground_port"));
    }
    /** 
     * Loads the parameter REFEREE_SITE_PORT from the configuration file.
     * @return parameter value
     */
    public int refereeSitePort() {
        return Integer.parseInt(prop.getProperty("refereesite_port"));
    }
}