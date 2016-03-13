/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Bench implements IReferee, ICoach, IContestant{
    
    private int nContestantsInBenchTeamA, nContestantsInBenchTeamB;
    private int nContestantsTeamA, nContestantsTeamB;
    
    private int nContestantsSelectedA = 0, nContestantsSelectedB = 0;
    private boolean calledByCoachA = false, followCoachA = false;
    private boolean calledByCoachB = false, followCoachB = false;
    private static int nContestantsSelectedMax = 3;
    
    private static boolean trialDecisionTaken = false, callTrialTaken = false;
            
    public Bench(int nContestantsTeamA, int nContestantsTeamB){
        this.nContestantsTeamA = this.nContestantsInBenchTeamA = nContestantsTeamA;
        this.nContestantsTeamB = this.nContestantsInBenchTeamB= nContestantsTeamB;
    }
    
    /* REFEREE METHODS */
    
    /**
     * the coaches are waken up by the referee in operation callTrial to start 
     * selecting next team
     */
    @Override
    public synchronized void callTrial() {
        while(this.nContestantsInBenchTeamA != this.nContestantsTeamA || this.nContestantsInBenchTeamB != this.nContestantsTeamB){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        callTrialTaken = true;
        notifyAll();
    }
    
    @Override
    public synchronized void waitForCallTrial(){
        while(!callTrialTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * the coaches are waken up in operation assertTrialDecision by the referee
     * the contestants are made to sleep for a random time interval in the 
     * simulation they block next and are waken up in operation assertTrialDecision by the referee
     */
    @Override
    public synchronized void assertTrialDecision() {
        trialDecisionTaken = true;
        notifyAll();
    }

    @Override
    public synchronized void waitForAssertTrialDecision(){
        while(!trialDecisionTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /* COACH METHODS */

    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command"
     */
    @Override
    public synchronized void reviewNotes(String team) {
        if(team.equals("A")){
            while(this.nContestantsInBenchTeamA != 5){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(team.equals("B")){
            while(this.nContestantsInBenchTeamB != 5){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team"
     */
    @Override
    public synchronized void callContestants(String team) {
        if(team.equals("A")){
            this.calledByCoachA = true;   
        }else if(team.equals("B")){
            this.calledByCoachB = true;
        }
        notifyAll();
    }
    
    @Override
    public synchronized void waitForCallContestants(String team){
        if(team.equals("A")){
            while(!this.calledByCoachA || this.nContestantsSelectedA == nContestantsSelectedMax){
                try {
                    wait();
                    this.nContestantsSelectedA++;
                    this.nContestantsInBenchTeamA--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(team.equals("B")){
            while(!this.calledByCoachB || this.nContestantsSelectedB == nContestantsSelectedMax){
                try {
                    wait();
                    this.nContestantsSelectedB++;
                    this.nContestantsInBenchTeamB--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /* PLAYERS METHODS */

    /**
     * the coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     */
    @Override
    public synchronized void followCoachAdvice(String team) {
        if(team.equals("A")){
            if(this.nContestantsSelectedA == 3){
                this.followCoachA = true;
                notifyAll();
            }
        }else if(team.equals("B")){
            if(this.nContestantsSelectedB == 3){
                this.followCoachB = true;
                notifyAll();
            }
        }
    }
    
    @Override
    public synchronized void waitForFollowCoachAdvice(String team){
        if(team.equals("A")){
            while(!this.followCoachA){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(team.equals("B")){
            while(!this.followCoachB){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     */
    @Override
    public synchronized void seatDown(String team){
        if(team.equals("A")){
            this.nContestantsInBenchTeamA++;
        }else if(team.equals("B")){
            this.nContestantsInBenchTeamB++;
        }
        notifyAll();

    }
    
}
