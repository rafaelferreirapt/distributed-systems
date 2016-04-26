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
public class MessageException extends Exception {

   private final Message msg;
    
    /**
    * Construct to create Message Exception with error and message
    * @param error
    * @param msg
    */
   public MessageException(String error, Message msg) {
        super(error);
        this.msg = msg;
    }
   
    /**
     * Return MessageObject
     * @return 
     */
    public Message getMessageObject() {
        return msg;
    }
}
