/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

import java.io.Serializable;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1001L;
    private MessageType type;
    private String team = null;
    private int idC = -1;
    private boolean endOfMatch = false;
    
    public Message(MessageType type) {
        this.type = type;
    }
    
    public Message(MessageType type, String team) {
        this.type = type;
        this.team = team;
    }
    
    public Message(MessageType type, String team, int idC) {
        this.type = type;
        this.team = team;
        this.idC = idC;
    }
    
    public Message(MessageType type, boolean endOfMatch) {
        this.type = type;
        this.endOfMatch = endOfMatch;
    }
    
    public MessageType getType(){
        return this.type;
    }
    
    public String getTeam(){
        return this.team;
    }
    
    public int getIdC(){
        return this.idC;
    }
    
    public boolean getEndOfMatch(){
        return this.endOfMatch;
    }
}
