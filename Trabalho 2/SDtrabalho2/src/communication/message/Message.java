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
    private MessageType type;
    private String str = null;
    private boolean endOfMatch = false;
    private int gameNumber = -1;
    private int integer = -1;
    private ContestantState contestant_state = null;
    private CoachState coach_state = null;
    private RefereeState referee_state = null;
    
    public Message(MessageType type) {
        this.type = type;
    }
    
    public Message(MessageType type, String str) {
        this.type = type;
        this.str = str;
    }
    
    
    public Message(MessageType type, int integer) {
        this.type = type;
        this.integer = integer;
    }
    
    public Message(MessageType type, String str, int integer) {
        this.type = type;
        this.str = str;
        this.integer = integer;
    }
    
    public Message(MessageType type, boolean endOfMatch) {
        this.type = type;
        this.endOfMatch = endOfMatch;
    }
    
    public Message(MessageType type, ContestantState contestant_state, String str, int integer){
        this.type = type;
        this.integer = integer;
        this.str = str;
        this.contestant_state = contestant_state;
    }
    
    public Message(MessageType type, CoachState coach_state, String str){
        this.type = type;
        this.str = str;
        this.coach_state = coach_state;
    }
    
    public Message(int gameNumber){
        this.gameNumber = gameNumber;
    }
    
    public Message(MessageType type, RefereeState referee_state){
        this.type = type;
        this.referee_state = referee_state;
    }
    
    public int getGameNumber(){
        return this.gameNumber;
    }
    
    public int getInteger(){
        return this.integer;
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
    
    public String getTeam(){
        return this.str;
    }
    
    public String getString(){
        return this.str;
    }
    
    public int getIdC(){
        return this.integer;
    }
    
    public boolean getEndOfMatch(){
        return this.endOfMatch;
    }
}
