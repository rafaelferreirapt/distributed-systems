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
    private int nCoachesAlerted = 0;
    private int nContestantsSelectedA = 0, nContestantsSelectedB = 0;
    private int nCoaches = 0;
    private int nFollowedA = 0, nFollowedB = 0;
    private boolean calledByCoachA = false, followCoachA = false;
    private boolean calledByCoachB = false, followCoachB = false;
    private boolean canCallTrial = true, canCallA = true, canCallB = true;
    private int[] selectedContestantsA = new int[3];
    private int[] selectedContestantsB = new int[3];
    private boolean endMatch = false;
    private int lastContestantUpA = 0;
    private int lastContestantUpB = 0;
    
    private static boolean trialDecisionTaken = false, callTrialTaken = false;
            
    public Bench(int nContestantsTeamA, int nContestantsTeamB){
        this.nContestantsTeamA = this.nContestantsInBenchTeamA = nContestantsTeamA;
        this.nContestantsTeamB = this.nContestantsInBenchTeamB= nContestantsTeamB;
    }
    
    /**
     *
     */

    
    /* REFEREE METHODS */
    
    /**
     * the coaches are waken up by the referee in operation callTrial to start 
     * selecting next team
     * called by the referee
     * 
     * -> canCallA or canCallB are used to inform the referee that can wake up the
     * coaches that can select the next team
     */
    @Override
    public synchronized void callTrial() {
        while(!this.canCallTrial){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.canCallTrial = false;
        callTrialTaken = true;
        notifyAll();
    }
    
    
    /**
     * The coaches are sleeping in this method waiting that the referee inform  
     * and can select the next team
     */
    @Override
    public synchronized void waitForCallTrial(){
        while(!callTrialTaken){
            try {
                wait();
                
                // when the referee sees that the match ends they must died
                if(this.endMatch){
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(++this.nCoaches == 2){
            callTrialTaken = false;
            this.nCoaches = 0;
        }
    }

    /**
     * the coaches are waken up in operation assertTrialDecision by the referee
     */
    @Override
    public synchronized void assertTrialDecision() {
        trialDecisionTaken = true;
        notifyAll();
    }

    /**
     * the coaches are waken up in operation assertTrialDecision by the referee
     */
    @Override
    public synchronized void waitForAssertTrialDecision(){
        while(!trialDecisionTaken){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(++this.nCoachesAlerted == 2){
            trialDecisionTaken = false;
            this.nCoachesAlerted = 0;

        }
    }
    
    /* COACH METHODS */

    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command"
     * @param team
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
     * @param team
     */
    @Override
    public synchronized void callContestants(String team) {
        if(team.equals("A")){
            this.selectedContestantsA = new int[3];
            for(int i=0; i<this.selectedContestantsA.length; i++){
                boolean repeated = false;
                int selected;
                
                do{
                    selected = (int)Math.ceil(Math.random() * 4 + 1);
                    repeated = false;
                    
                    for(int j=0; j<i; j++){
                        if(selected==this.selectedContestantsA[j]){
                            repeated = true;
                            break;
                        }
                    }
                }while(repeated);
                this.selectedContestantsA[i] = selected;       
            }
            this.calledByCoachA = true;  
        }else if(team.equals("B")){
            this.selectedContestantsB = new int[3];
            for(int i=0; i<this.selectedContestantsB.length; i++){
                boolean repeated = false;
                int selected;
                
                do{
                    selected = (int)Math.ceil(Math.random() * 4 + 1);
                    repeated = false;
                    
                    for(int j=0; j<i; j++){
                        if(selected==this.selectedContestantsB[j]){
                            repeated = true;
                            break;
                        }
                    }
                }while(repeated);
                this.selectedContestantsB[i] = selected;       
            }
            this.calledByCoachB = true;
        }
        
        notifyAll();
    }
    
    private boolean amISelected(String team, int idC){
        if(team.equals("A")){
            for(int i=0; i<this.selectedContestantsA.length; i++){
                if(this.selectedContestantsA[i]==idC){
                    return true;
                }
            }
        }else if(team.equals("B")){
            for(int i=0; i<this.selectedContestantsB.length; i++){
                if(this.selectedContestantsB[i]==idC){
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public synchronized void waitForCallContestants(String team, int idC){
        if(team.equals("A")){
            while(!this.calledByCoachA || !this.amISelected(team, idC)){
                try {
                    wait();
                    if(this.endMatch){
                        break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.nContestantsInBenchTeamA--;
            //System.err.println("Entrou A");

            if(++this.nContestantsSelectedA == 3){
                this.calledByCoachA = false;
                this.lastContestantUpA = idC;
            }
        }else if(team.equals("B")){
            while(!this.calledByCoachB || !this.amISelected(team, idC)){
                try {
                    wait();
                    if(this.endMatch){
                        break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.nContestantsInBenchTeamB--;
            //System.err.println("Entrou B");
            if(++this.nContestantsSelectedB == 3){
                this.calledByCoachB = false;
                this.lastContestantUpB = idC;
            }
        }
    }
    
    /* PLAYERS METHODS */

    /**
     * the coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     * @param team
     */
    @Override
    public synchronized void followCoachAdvice(String team, int idC) {
        if(team.equals("A")){
            if(idC == this.lastContestantUpA){
                this.followCoachA = true;
                this.lastContestantUpA = 0;
            }
        }else if(team.equals("B")){
            if(idC == this.lastContestantUpB){
                this.followCoachB = true;
                this.lastContestantUpB = 0;
            }
        }
        notifyAll();

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
     
            this.followCoachA = false;
            this.nFollowedA = 0;
  
        }else if(team.equals("B")){
            while(!this.followCoachB){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.followCoachB = false;
            this.nFollowedB = 0;
            
        }
    }
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     * @param team
     */
    @Override
    public synchronized void seatDown(String team){
        if(team.equals("A")){
            this.nContestantsInBenchTeamA++;
            this.nContestantsSelectedA--;               
        }else if(team.equals("B")){
            this.nContestantsInBenchTeamB++;
            this.nContestantsSelectedB--;
        }
        if(this.nContestantsInBenchTeamA == this.nContestantsTeamA && this.nContestantsInBenchTeamB == this.nContestantsTeamB){
            this.canCallTrial = true;
            notifyAll();
        }

    }
    
    @Override
    public synchronized void wakeUp(){
        this.endMatch = true;
        notifyAll();
    }
    
}
