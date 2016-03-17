/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

import java.util.logging.Level;
import java.util.logging.Logger;
import general_info_repo.Match;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Bench implements IReferee, ICoach, IContestant{
    
    private int nContestantsInBench;
    private int nCoachesAlerted = 0;
    private int nContestantsSelectedA = 0, nContestantsSelectedB = 0;
    private boolean followCoachA = false;
    private boolean followCoachB = false;
    private boolean canCallTrial = true;
    private int coachesWaitCallTrial = 0;
    private int[] selectedContestantsA = new int[3];
    private int[] selectedContestantsB = new int[3];
    private boolean endMatch = false, selectedA = false, selectedB = false;
    private int lastContestantUpA = 0;
    private int lastContestantUpB = 0;
    private Match match = Match.getInstance();
    
    private boolean trialDecisionTaken = false;
    private boolean callTrialTaken = false;
            
    public Bench(int nContestantsTeamA, int nContestantsTeamB){
        this.nContestantsInBench = nContestantsTeamA + nContestantsTeamB;
    }
      
    /**
     * the referee must wait that all the coaches are waiting for the call trial
     * and all the contestants are in the bench, then the referee can continue 
     * the trial
     */
    @Override
    public synchronized void callTrial() {
        while(this.coachesWaitCallTrial != 2 || this.nContestantsInBench != 10){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.nContestantsSelectedA=0;
        this.nContestantsSelectedB=0;
        
        this.coachesWaitCallTrial=0;
        
        this.callTrialTaken=true;
        notifyAll();
    }
    
    
    /**
     * The coaches are sleeping in this method waiting that the referee inform  
     * and can select the next team
     */
    @Override
    public synchronized void waitForCallTrial(){
        this.callTrialTaken = false;
        this.coachesWaitCallTrial++;
        notifyAll();
        
        while(!this.callTrialTaken){
            try {
                wait();
                
                // when the referee sees that the match ends they must died
                if(this.endMatch){
                    return;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        while(this.nContestantsInBench < 10){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team"
     * @param team
     */
    @Override
    public synchronized void callContestants(String team) {
        int tmp[] = new int[3];
        
        for(int i=0; i<tmp.length; i++){
            boolean repeated = false;
            int selected;

            do{
                selected = (int)Math.ceil(Math.random() * 4 + 1);
                repeated = false;

                for(int j=0; j<i; j++){
                    if(selected==tmp[j]){
                        repeated = true;
                        break;
                    }
                }
            }while(repeated);
            tmp[i] = selected;
        }
        
        if(team.equals("A")){
            this.selectedContestantsA = tmp;
        }else if(team.equals("B")){
            this.selectedContestantsB = tmp;
        }
    }
    
    private boolean amISelected(String team, int idC){
        if(team.equals("A")){
            for(int i=0; i<this.selectedContestantsA.length; i++){
                if(this.selectedContestantsA[i]==idC){
                    this.selectedContestantsA[i] = 0;
                    return true;
                }
            }
        }else if(team.equals("B")){
            for(int i=0; i<this.selectedContestantsB.length; i++){
                if(this.selectedContestantsB[i]==idC){
                    this.selectedContestantsB[i] = 0;
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public synchronized void waitForCallContestants(String team, int idC){
        if(team.equals("A")){
            while(!this.selectedA || !this.amISelected(team, idC)){
                try {
                    wait();
                    if(this.endMatch){
                        break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(this.endMatch){
                return;
            }
            this.nContestantsInBench--;
            //System.err.println("Entrou A");

            if(++this.nContestantsSelectedA == 3){
                this.lastContestantUpA = idC;
            }
        }else if(team.equals("B")){
            while(!this.selectedB || !this.amISelected(team, idC)){
                try {
                    wait();
                    if(this.endMatch){
                        break;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(this.endMatch){
                return;
            }
            this.nContestantsInBench--;
            //System.err.println("Entrou B");
            if(++this.nContestantsSelectedB == 3){
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
     * @param idC
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
            this.selectedA = true;
            notifyAll();
            
            while(!this.followCoachA){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.selectedA = false;
            this.followCoachA = false;
        }else if(team.equals("B")){
            this.selectedB = true;
            notifyAll();
            
            while(!this.followCoachB){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.selectedB = false;
            this.followCoachB = false;
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
        if(++this.nContestantsInBench==10){
            notifyAll();
        }
    }
    
    @Override
    public synchronized void wakeUp(){
        this.endMatch = true;
        notifyAll();
    }
    
}
