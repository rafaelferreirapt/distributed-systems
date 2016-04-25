/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import communication.message.Message;
import communication.message.MessageType;
import communication.proxy.ClientProxyWrapper;
import java.util.ArrayList;
import settings.NodeSettsProxy;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class CoachRun {
    
    private static int N_COACHS;
    private static String[] teams;
    
    public static void main(String [] args) {    
        NodeSettsProxy proxy = new NodeSettsProxy(); 
        N_COACHS = proxy.N_COACHS();
        teams = proxy.teams();
        
        ArrayList<Coach> coachs = new ArrayList<>(N_COACHS);
        BenchProxy bp = new BenchProxy();
        RefereeSiteProxy rsp = new RefereeSiteProxy();
        LogProxy log = new LogProxy();
        
        for (int i = 0; i < N_COACHS; i++){
            coachs.add(new Coach((bench.ICoach) bp, (referee_site.ICoach) rsp, teams[i], (general_info_repo.ICoach) log));
        }
        
        
        System.out.println("Number of coachs: " + coachs.size());
        
        for (Coach c : coachs)
            c.start();
        
        for (Coach c : coachs) { 
            try { 
                c.join ();
            } catch (InterruptedException e) {}
        }
        
        System.out.println("Sending TERMINATE message to the logging");
        
        /* SEND TO LOG THAT COACH HAS FINISHED */
        ClientProxyWrapper.connect(proxy.SERVER_HOSTS().get("Log"), 
                proxy.SERVER_PORTS().get("Log"), 
                new Message(MessageType.TERMINATE));
    }
}
