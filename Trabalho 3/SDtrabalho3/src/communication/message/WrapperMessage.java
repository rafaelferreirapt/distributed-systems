/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

/**
 * Wrapper Message
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class WrapperMessage {
    
    private Message m;
    
    /**
     * Set Message
     * @param m
     */
    public void setMessage(Message m){
        this.m = m;
    }
    
    /**
     * Return Message
     * @return
     */
    public Message getMessage(){
        return this.m;
    }
}
