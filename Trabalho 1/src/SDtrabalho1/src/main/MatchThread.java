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
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class MatchThread extends Thread{

    private static Bench bench;
    private static Playground playground;
    private static RefereeSite referee_site;
    private static Log lg;
    private static Coach [] coachs;
    private static Contestant [] contestants;
    private static Referee ref;
    
    @Override
    public void run() {
        /* START THE ENTITIES */
        int nCoaches = 2;
        String teams[] = {"A", "B"};
        int nContestants = 10;
        
        assert nCoaches == teams.length;
        
        bench = new Bench(nContestants, nContestants);
        playground = new Playground();
        referee_site = new RefereeSite();
        lg = new Log();
        
        ref =  new Referee((playground.IReferee) playground, (bench.IReferee) bench, (referee_site.IReferee) referee_site, lg);
        ref.start();
        
        coachs =  new Coach [nCoaches];
        for(int i = 0; i<nCoaches; i++){
            coachs[i] = new Coach((bench.ICoach) bench,(referee_site.ICoach) referee_site, i, teams[i], lg);
            coachs[i].start();
        }
        
        contestants = new Contestant[nContestants];
        
        for(int i = 0; i<nContestants; i++){
            if(i < (nContestants/teams.length)){
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[0], lg);
            }else{
                contestants[i] = new Contestant((playground.IContestant) playground,(bench.IContestant) bench, (referee_site.IContestant) referee_site, i, teams[1], lg);
            }
            contestants[i].start();
        }
            
        for(int i = 0; i<nCoaches; i++){
            try {
                coachs[i].join();
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        for(int i = 0; i<nContestants; i++){
            try {
                contestants[i].join();
            } catch (InterruptedException ex) {
                //Escrever para o log
            }
        }
        
        try {
            ref.join();
        } catch (InterruptedException ex) {
            //Escrever para o log
        }
    }
    
}