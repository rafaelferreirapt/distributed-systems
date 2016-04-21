/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import settings.NodeSetts;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class ContestantRun {
    
    public static void main(String [] args) {        
        ArrayList<Contestant> contestants = new ArrayList<>(NodeSetts.nContestantsTeam*2);
        BenchProxy bp = new BenchProxy();
        RefereeSiteProxy rsp = new RefereeSiteProxy();
        PlaygroundProxy pp = new PlaygroundProxy();
        LogProxy log = new LogProxy();
        
        for (int i = 0; i < contestants.size(); i++){
            if(i < NodeSetts.nContestantsTeam){
                contestants.add(new Contestant((playground.IContestant) pp, (bench.IContestant) bp, (referee_site.IContestant) rsp, i, 
                        NodeSetts.teams[0], (general_info_repo.IContestant) log));
            }else{
                contestants.add(new Contestant((playground.IContestant) pp, (bench.IContestant) bp, (referee_site.IContestant) rsp, i, 
                        NodeSetts.teams[0], (general_info_repo.IContestant) log));
            }
        }
        
        
        System.out.println("Number of contestants: " + contestants.size());
        
        for (Contestant c : contestants)
            c.start();
        
        for (Contestant c : contestants) { 
            try { 
                c.join ();
            } catch (InterruptedException e) {}
        }
        
        System.out.println("Sending TERMINATE message to the logging");
        
        
        /* SEND TO LOG THAT COACH HAS FINISHED */
        
    }
}
