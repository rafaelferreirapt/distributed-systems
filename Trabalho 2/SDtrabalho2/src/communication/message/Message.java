/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

import entities.CoachState;
import entities.ContestantState;
import entities.RefereeState;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Message class
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1001L;
    private MessageType type = null;
    
    private String str = null;
    private String[] str_arr = null;
    private boolean bool = false;
    private int integer = -1;
    
    private ContestantState contestant_state = null;
    private CoachState coach_state = null;
    private RefereeState referee_state = null;
    
    private HashMap<?, ?> map = null;
    
    /**
     * Construct to create Message with type
     * @param type
     */
    public Message(MessageType type) {
        this.type = type;
    }
    
    /**
     * Construct to create Message with type and String
     * @param type
     * @param str
     */
    public Message(MessageType type, String str) {
        this(type);
        this.str = str;
    }
    
    /**
     * Construct to create Message with type and Integer
     * @param type
     * @param integer
     */
    public Message(MessageType type, int integer) {
        this(type);
        this.integer = integer;
    }
    
    /**
     * Construct to create Message with type, String and Integer
     * @param type
     * @param str
     * @param integer
     */
    public Message(MessageType type, String str, int integer) {
        this(type, str);
        this.integer = integer;
    }
    
    /**
     * Construct to create Message with type and Boolean
     * @param type
     * @param bool
     */
    public Message(MessageType type, boolean bool) {
        this(type);
        this.bool = bool;
    }
    
    /**
     * Construct to create Message with type, Contestant State, String and Integer
     * @param type
     * @param contestant_state
     * @param str
     * @param integer
     */
    public Message(MessageType type, ContestantState contestant_state, String str, int integer){
        this(type, str, integer);
        this.contestant_state = contestant_state;
    }
    
    /**
     * Construct to create Message with type, Coach State and String
     * @param type
     * @param coach_state
     * @param str
     */
    public Message(MessageType type, CoachState coach_state, String str){
        this(type, str);
        this.coach_state = coach_state;
    }
    
    /**
     * Construct to create Message with type and Referee State
     * @param type
     * @param referee_state
     */
    public Message(MessageType type, RefereeState referee_state){
        this(type);
        this.referee_state = referee_state;
    }
    
    /**
     * Construct to create Message with type and HashMap
     * @param type
     * @param map
     */
    public Message(MessageType type, HashMap<?, ?> map){
        this(type);
        this.map = map;
    }
    
    /**
     * Construct to create Message with type and String Array
     * @param type
     * @param str_arr
     */
    public Message(MessageType type, String[] str_arr){
        this(type);
        this.str_arr = str_arr;
    }
    
    /**
     * Return Contestant State
     * @return 
     */
    public ContestantState getContestantState(){
        return this.contestant_state;
    }
    
    /**
     * Return Referee State
     * @return 
     */
    public RefereeState getRefereeState(){
        return this.referee_state;
    }
    
    /**
     * Return Coach State
     * @return 
     */
    public CoachState getCoachState(){
        return this.coach_state;
    }
    
    /**
     * Return Message Type
     * @return 
     */
    public MessageType getType(){
        return this.type;
    }
    
    /**
     * Return Integer
     * @return 
     */
    public int getInteger(){
        return this.integer;
    }
    
    /**
     * Return String
     * @return 
     */
    public String getString(){
        return this.str;
    }
    
    /**
     * Return Boolean
     * @return 
     */
    public boolean getBoolean(){
        return this.bool;
    }
    
    /**
     * Return String Array
     * @return 
     */
    public String[] getStringArr(){
        return this.str_arr;
    }
    
    /**
     * Return String HashMap
     * @return 
     */
    public HashMap<String, String> getStrStrMap(){
        return (HashMap<String, String>) this.map;
    }
    
    /**
     * Return Integer HashMap
     * @return 
     */
    public HashMap<String, Integer> getStrIntMap(){
        return (HashMap<String, Integer>) this.map;
    }
}