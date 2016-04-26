/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.proxy.ClientProxy;
import settings.NodeSettsProxy;

/**
 * Referee Run
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeRun {
    
    public static void main(String [] args) {
        BenchProxy bp = new BenchProxy();
        RefereeSiteProxy rsp = new RefereeSiteProxy();
        PlaygroundProxy pp = new PlaygroundProxy();
        LogProxy log = new LogProxy();
        
        Referee ref = new Referee((playground.IReferee) pp, (bench.IReferee) bp, (referee_site.IReferee) rsp, 
                (general_info_repo.IReferee) log);
        
        System.out.println("Number of referees: 1");
       
        ref.start();
        
        try { 
            ref.join ();
        } catch (InterruptedException e) {}
        
        System.out.println("Sending TERMINATE message to the logging");
        
        /* SEND TO LOG THAT COACH HAS FINISHED */
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        ClientProxy.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
        
        System.out.println("Done!");
    }
}
