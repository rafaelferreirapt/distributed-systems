/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package main;

import bench.Bench;
import entities.Coach;
import entities.Contestant;
import entities.Referee;
import general_info_repo.Log;
import playground.Playground;
import referee_site.RefereeSite;

/**
 * Game of the Rope main!
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Main{
    private static Bench bench;
    private static Playground playground;
    private static RefereeSite referee_site;
    private static Log lg;
    private static Coach [] coachs;
    private static Contestant [] contestants;
    private static Referee ref;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        /* START THE ENTITIES */
        int nCoaches = 2;
        String teams[] = {"A", "B"};
        int nContestants = 10;
        
        assert nCoaches == teams.length;
        
        bench = new Bench(nContestants/2, nContestants/2);
        playground = new Playground();
        referee_site = new RefereeSite();

        lg = Log.getInstance();

        ref =  new Referee((playground.IReferee) playground, (bench.IReferee) bench, (referee_site.IReferee) referee_site);
        
        coachs =  new Coach [nCoaches];
        for(int i = 0; i<nCoaches; i++){
            coachs[i] = new Coach((bench.ICoach) bench,(referee_site.ICoach) referee_site, teams[i]);
        }
        
        contestants = new Contestant[nContestants];
        
        for(int i = 0; i<nContestants; i++){
            if(i < (nContestants/teams.length)){
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i+1, teams[0]);
            }else{
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i-4, teams[1]);
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
