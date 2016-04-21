/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package main;

import entities.BenchProxy;
import entities.Coach;
import entities.Contestant;
import entities.LogProxy;
import entities.PlaygroundProxy;
import entities.Referee;
import entities.RefereeSiteProxy;
import settings.NodeSetts;

/**
 * Game of the Rope main!fg
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Main{
    private static BenchProxy bench;
    private static RefereeSiteProxy referee_site;
    private static PlaygroundProxy playground;
    private static LogProxy lg;
    private static Coach [] coachs;
    private static Contestant [] contestants;
    private static Referee ref;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        /* START THE ENTITIES */
        int nCoaches = NodeSetts.nCoachs;
        String teams[] = NodeSetts.teams;
        int nContestants = NodeSetts.nContestantsTeam*2;
        
        assert nCoaches == teams.length;
        
        bench = new BenchProxy();
		referee_site = new RefereeSiteProxy();
        playground = new PlaygroundProxy();

        lg = new LogProxy();
       
        ref =  new Referee((playground.IReferee) playground, (bench.IReferee) bench, 
                (referee_site.IReferee) referee_site, (general_info_repo.IReferee) lg);
        
        coachs =  new Coach [nCoaches];
        for(int i = 0; i<nCoaches; i++){
            coachs[i] = new Coach((bench.ICoach) bench,(referee_site.ICoach) referee_site, teams[i],
            (general_info_repo.ICoach) lg);
        }
        
        contestants = new Contestant[nContestants];
        
        for(int i = 0; i<nContestants; i++){
            if(i < (nContestants/teams.length)){
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, 
                        (referee_site.IContestant) referee_site, i+1, teams[0], (general_info_repo.IContestant) lg);
            }else{
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, 
                        (referee_site.IContestant) referee_site, i-(nContestants/teams.length-1), teams[1], 
                        (general_info_repo.IContestant) lg);
            }
        }
        
        /* now start */
        ref.start();
        
        for(int i = 0; i<nCoaches; i++){
            coachs[i].start();
        }
        
        for(int i = 0; i<nContestants; i++){
            contestants[i].start();
        }
        
        /* now join */
        
        for(int i = 0; i<nCoaches; i++){
            try {
                coachs[i].join();
                System.err.println("Coach Died: " + i);
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        for(int i = 0; i<nContestants; i++){
            try {
                contestants[i].join();
                System.err.println("Contestant Died: " + i);

            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        try {
            ref.join();
            System.err.println("Referee Died");

        } catch (InterruptedException ex) {
            //Escrever para o log
        }
        
        lg.writeEnd();
    }
}
