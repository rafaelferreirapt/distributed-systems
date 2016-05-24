/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package bench;

import interfaces.RegisterInterface;
import interfaces.bench.BenchInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.Constants;
import structures.RegistryConfig;
import structures.VectorTimestamp;


/**
 * Bench instance.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Bench implements BenchInterface{
    
    private int nContestantsInBench;
    private int nCoachesAlerted = 0;
    private int nContestantsSelectedA = 0, nContestantsSelectedB = 0;
    private boolean followCoachA = false;
    private boolean followCoachB = false;
    
    private final int NUMBER_CONTESTANTS;
    private final int N_CONTESTANTS_TEAM;
    
    private int coachesWaitCallTrial = 0;
    private int[] selectedContestantsA = new int[3];
    private int[] selectedContestantsB = new int[3];
    private boolean endMatch = false, selectedA = false, selectedB = false;
    private int lastContestantUpA = 0;
    private int lastContestantUpB = 0;
    
    private boolean trialDecisionTaken = false;
    private boolean callTrialTaken = false;
    
    private final VectorTimestamp clocks;

            
    /**
     * We need to know how many contestants there are in the bench,
     * for that we pass how many contestants there are in the team A or B
     * @param nContestantsTeamA number of contestants of the team A
     * @param nContestantsTeamB number of contestants of the team B
     */
    public Bench(int nContestantsTeamA, int nContestantsTeamB){
        this.nContestantsInBench = this.NUMBER_CONTESTANTS = nContestantsTeamA + nContestantsTeamB;
        this.N_CONTESTANTS_TEAM = nContestantsTeamA;
        this.clocks = new VectorTimestamp(this.NUMBER_CONTESTANTS + Constants.N_COACHS + 2, 0);
    }
      
    /**
     * The referee must wait that all the coaches are waiting for the call trial
     * and all the contestants are in the bench, then the referee can continue 
     * the trial, it will reset all the flags, the flag to the contestants selected
     * in the Team A or B and set the coaches wait for call trial to zero. The
     * coachs are sleeping and need the flag callTrialTaken true to procede. In the
     * final there is a notifyAll() to wake up all the entities in the Bench.
     */
    @Override
    public synchronized VectorTimestamp callTrial(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        while(this.coachesWaitCallTrial != 2 || this.nContestantsInBench != NUMBER_CONTESTANTS){

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
        return this.clocks.clone();
    }
    
    
    /**
     * The coaches are sleeping in this method waiting that the referee inform  
     * and can select the next team.
     */
    @Override
    public synchronized VectorTimestamp waitForCallTrial(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        this.callTrialTaken = false;
        this.coachesWaitCallTrial++;
        notifyAll();
        
        while(!this.callTrialTaken){
            try {
                wait();
                
                // when the referee sees that the match ends they must died
                if(this.endMatch){
                    return this.clocks.clone();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return this.clocks.clone();
    }

    /**
     * The referee will waken up the coaches with the trialDecisionTaken = true and 
     * with the notifyAll().
     */
    @Override
    public synchronized VectorTimestamp assertTrialDecision(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        trialDecisionTaken = true;
        notifyAll();
        return this.clocks.clone();
    }

    /**
     * The coaches will wait until the referee make the decision of notify the
     * referees in the assertTrialDecision.
     */
    @Override
    public synchronized VectorTimestamp waitForAssertTrialDecision(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    }
    
    /**
     * In coach life cycle, transition between "watch trial" and "wait for referee command".
     * The coach will wait until 10 contestants are in the bench and then will continue.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public synchronized VectorTimestamp reviewNotes(String team, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        while(this.nContestantsInBench < NUMBER_CONTESTANTS){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return this.clocks.clone();
    }

    /**
     * In coach life cycle, transition between "wait for referee command" and "assemble team".
     * The coach will select randomly the contestants, this will be the strategy, select random
     * the contestants. The contestants will make one "pool" to see if they are in the array of
     * the players selected.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public synchronized VectorTimestamp callContestants(String team, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        int tmp[] = new int[3];
        
        for(int i=0; i<tmp.length; i++){
            boolean repeated = false;
            int selected;

            do{
                selected = (int)Math.ceil(Math.random() * (this.N_CONTESTANTS_TEAM));
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
        return this.clocks.clone();
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
    
    /**
     * The contestants will wait here to be called to the trial in the bench.
     * If the the team YXZ is select, then the contestant will verify if there is
     * in the array of selected contestants to do the best in the rope game.
     * When the contestant leaves the bench then is decremented one variable that
     * conunts how many contestants there is in the bench. One more important thing,
     * we need the ID of the last contestant that is up to then inform the coach that
     * all the contestants are ready to go.
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    @Override
    public synchronized VectorTimestamp waitForCallContestants(String team, int idC, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
                return this.clocks.clone();
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
                return this.clocks.clone();
            }
            this.nContestantsInBench--;
            //System.err.println("Entrou B");
            if(++this.nContestantsSelectedB == 3){
                this.lastContestantUpB = idC;
            }
        }
        return this.clocks.clone();
    }
    
    /**
     * The coaches are waken up in operation followCoachAdvice by the last of 
     * their selected contestants to stand in position.
     * In Contestants life cycle, transition between "seat at the bench" and "stand in position"
     * @param team Team identifier, can be A or B.
     * @param idC The contestant ID.
     */
    @Override
    public synchronized VectorTimestamp followCoachAdvice(String team, int idC, VectorTimestamp vt) throws RemoteException {
        this.clocks.update(vt);
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
        return this.clocks.clone();
    }
    
    /**
     * The coach will wait until the last contestant stand in position to then 
     * follow the coach advice. The flags will be resetted.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public synchronized VectorTimestamp waitForFollowCoachAdvice(String team, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
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
        return this.clocks.clone();
    }
    
    /**
     * In Contestants life cycle, transition between "doYourBest" and "seat at the bench"
     * SEAT_AT_THE_BENCH – blocking state the contestants are waken up in operation 
     * callContestants by their coaches if they are selected to join the next trial
     * @param team Team identifier, can be A or B.
     */
    @Override
    public synchronized VectorTimestamp seatDown(String team, VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        if(++this.nContestantsInBench==NUMBER_CONTESTANTS){
            notifyAll();
        }
        
        return this.clocks.clone();
    }
    
    /**
     * This method will wakeup all the entities in the bench and declare that
     * the match was ended.
     * @return 
     */
    @Override
    public synchronized VectorTimestamp wakeUp(VectorTimestamp vt) throws RemoteException{
        this.clocks.update(vt);
        this.endMatch = true;
        notifyAll();
        return this.clocks.clone();
    }

    @Override
    public void signalShutdown() throws RemoteException {
        RegisterInterface reg = null;
        Registry registry = null;

        String rmiRegHostName;
        int rmiRegPortNumb;
        
        RegistryConfig rc = new RegistryConfig("config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.benchNameEntry;

        
        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Bench registration exception: " + e.getMessage());
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Bench not bound exception: " + e.getMessage());
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Bench.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Bench closed.");
    }
}
