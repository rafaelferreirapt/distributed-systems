/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.message;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class WrapperMessage {
    
    private Message m;
    
    public void setMessage(Message m){
        this.m = m;
    }
    
    public Message getMessage(){
        return this.m;
    }
}
