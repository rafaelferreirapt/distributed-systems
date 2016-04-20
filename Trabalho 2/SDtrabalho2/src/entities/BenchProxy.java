/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import bench.ICoach;
import bench.IContestant;
import bench.IReferee;
import communication.message.MessageType;
import communication.message.WrapperMessage;
import communication.proxy.ClientProxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class BenchProxy implements IReferee, ICoach, IContestant{
    
    @Override
    public void callTrial() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.callTrial);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void assertTrialDecision() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.assertTrialDecision);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void wakeUp() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.wakeUp);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void reviewNotes(String team) {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.reviewNotes, team);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName()));  
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void callContestants(String team) {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.callContestants, team);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForCallTrial() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.waitForCallTrial);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForAssertTrialDecision() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.waitForAssertTrialDecision);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForFollowCoachAdvice(String team) {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.waitForFollowCoachAdvice, team);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName()));  
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void followCoachAdvice(String team, int idC) {
     WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.followCoachAdvice, team, idC);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void seatDown(String team) {
     WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.seatDown, team);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
            System.exit(1);
        }
    }

    @Override
    public void waitForCallContestants(String team, int idC) {
     WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.benchServerName, 
                NodeSetts.benchServerPort, result, MessageType.waitForCallContestants, team, idC);
        
        cp.start();
        
        try {
            // System.out.println("Init Join "+ (new Object(){}.getClass().getEnclosingMethod().getName())); 
            cp.join(); 
            // System.out.println("Final Join");
        } catch (InterruptedException ex) {
            Logger.getLogger(BenchProxy.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        if (result.getMessage().getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + result.getMessage().toString());
                System.exit(1);
        }
    }
    
}
