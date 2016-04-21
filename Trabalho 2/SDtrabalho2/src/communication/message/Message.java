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

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1001L;
    private MessageType type = null;
    
    private String str = null;
    private boolean bool = false;
    private int integer = -1;
    
    private ContestantState contestant_state = null;
    private CoachState coach_state = null;
    private RefereeState referee_state = null;
    
    public Message(MessageType type) {
        this.type = type;
    }
    
    public Message(MessageType type, String str) {
        this(type);
        this.str = str;
    }
    
    
    public Message(MessageType type, int integer) {
        this(type);
        this.integer = integer;
    }
    
    public Message(MessageType type, String str, int integer) {
        this(type, str);
        this.integer = integer;
    }
    
    public Message(MessageType type, boolean bool) {
        this(type);
        this.bool = bool;
    }
    
    public Message(MessageType type, ContestantState contestant_state, String str, int integer){
        this(type, str, integer);
        this.contestant_state = contestant_state;
    }
    
    public Message(MessageType type, CoachState coach_state, String str){
        this(type, str);
        this.coach_state = coach_state;
    }
    
    public Message(MessageType type, RefereeState referee_state){
        this(type);
        this.referee_state = referee_state;
    }
    
    public ContestantState getContestantState(){
        return this.contestant_state;
    }
    
    public RefereeState getRefereeState(){
        return this.referee_state;
    }
    
    public CoachState getCoachState(){
        return this.coach_state;
    }
    
    public MessageType getType(){
        return this.type;
    }
    
    public int getInteger(){
        return this.integer;
    }
    
    public String getString(){
        return this.str;
    }
    
    public boolean getBoolean(){
        return this.bool;
    }
}