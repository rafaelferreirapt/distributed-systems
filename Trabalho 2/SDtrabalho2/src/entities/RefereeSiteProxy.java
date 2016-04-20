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
import referee_site.ICoach;
import referee_site.IContestant;
import referee_site.IReferee;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeSiteProxy implements IReferee, ICoach, IContestant{

    @Override
    public void annouceNewGame() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.annouceNewGame);
        
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
    public void declareGameWinner() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.declareGameWinner);
        
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
    public void declareMatchWinner() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.declareMatchWinner);
        
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
    public boolean endOfMatch() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.endOfMatch);
        
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
        
        return result.getMessage().getEndOfMatch();
    }

    @Override
    public void waitForInformReferee() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.waitForInformReferee);
        
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
    public void waitForAmDone() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.waitForAmDone);
        
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
    public void waitAllPositioned() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.waitAllPositioned);
        
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
    public void informReferee(String team) {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.informReferee, team);
        
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
    public void amDone() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.amDone);
        
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
    public void positioned() {
        WrapperMessage result = new WrapperMessage();
        
        ClientProxy cp = new ClientProxy(NodeSetts.refereeSiteServerName, 
                NodeSetts.refereeSiteServerPort, result, MessageType.positioned);
        
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
