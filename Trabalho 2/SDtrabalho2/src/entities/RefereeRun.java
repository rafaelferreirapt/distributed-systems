/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class RefereeRun {
    
    public static void main(String [] args) {
        BenchProxy bp = new BenchProxy();
        RefereeSiteProxy rsp = new RefereeSiteProxy();
        PlaygroundProxy pp = new PlaygroundProxy();
        
        Referee ref = new Referee((playground.IReferee) pp, (bench.IReferee) bp, (referee_site.IReferee) rsp);
        
        System.out.println("Number of referees: 1");
       
        ref.start();
        
        try { 
            ref.join ();
        } catch (InterruptedException e) {}
        
        System.out.println("Sending TERMINATE message to the logging");
        
        /* SEND TO LOG THAT COACH HAS FINISHED */
        
    }
}
