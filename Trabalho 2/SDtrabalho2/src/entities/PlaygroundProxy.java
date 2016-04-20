/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import playground.IContestant;
import playground.IReferee;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class PlaygroundProxy implements IReferee, IContestant{
    
    @Override
    public void startTrial() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, result, MessageType.startTrial);
        
        cp.start();
        
        try {
            System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            System.out.println("Final Join"+ (new Object(){}.getClass().getEnclosingMethod().getName()));
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaygroundProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void assertTrialDecision() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, result, MessageType.assertTrialDecision);
        
        cp.start();
        
        try {
            System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            System.out.println("Final Join"+ (new Object(){}.getClass().getEnclosingMethod().getName()));
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaygroundProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void pullTheRope(int id, String team) {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, result, MessageType.pullTheRope, team, id);
        
        cp.start();
        
        try {
            System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            System.out.println("Final Join"+ (new Object(){}.getClass().getEnclosingMethod().getName()));
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaygroundProxy.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForStartTrial() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, result, MessageType.waitForStartTrial);
        
        cp.start();
        
        try {
            System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            System.out.println("Final Join"+ (new Object(){}.getClass().getEnclosingMethod().getName()));
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaygroundProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForAssertTrialDecision() {
        
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.playgroundServerName, 
                NodeSetts.playgroundServerPort, result, MessageType.waitForAssertTrialDecision);
        
        cp.start();
        
        try {
            System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            System.out.println("Final Join"+ (new Object(){}.getClass().getEnclosingMethod().getName()));
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaygroundProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }
}
