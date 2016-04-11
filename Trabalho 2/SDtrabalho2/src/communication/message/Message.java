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
    
    public Message(MessageType type) {
        this.type = type;
    }
}
