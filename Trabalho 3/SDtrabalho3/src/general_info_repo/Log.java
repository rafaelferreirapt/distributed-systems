/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import interfaces.RegisterInterface;
import interfaces.bench.BenchInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import interfaces.log.LogInterface;
import interfaces.playground.PlaygroundInterface;
import interfaces.referee_site.RefereeSiteInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.Constants;
import structures.CoachState;
import structures.ContestantState;
import structures.RefereeState;
import structures.RegistryConfig;

/**
 * The log will be the gateway to all the information of the match, games, trials,
 * strengths of the players. It will be responsible too for write in one file with
 * the log of the match.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Log implements LogInterface{
    
    private final Match match = Match.getInstance();
   
    private final int N_COACHS;
    private final int N_CONTESTANTS;
    private int numberEntitiesRunning = 3;
    
    /**
     *  File where the log will be saved
     */
    private final File log;
    
    private static PrintWriter pw;
    
    /**
     * This will instantiate the log Class
     * Name of the file where the log will be saved
     * @param filename where the log will be saved.
     */
    public Log(String filename){
        N_COACHS = Constants.N_COACHS;
        N_CONTESTANTS = Constants.N_CONTESTANTS_TEAM * 2;
        
        if(filename.length()==0){
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
            filename = "GameOfTheRope_" + date.format(today) + ".log";
        }
        this.log = new File(filename);
        this.writeInit();
    }
    
    /**
     * This method writes the head of the log file.
     */
    private void writeInit(){
        try{
            pw = new PrintWriter(log);
            pw.println("                               Game of the Rope - Description of the internal state");
            
            String head = "Ref  Coa 1";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Cont " + Integer.toString(i);
            }
            head += " Coa 2";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Cont " + Integer.toString(i);
            }
            head += "       Trial";
            pw.println(head);
            
            head = "Sta  Stat";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Sta SG";
            }
            head += "  Stat";
            for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
                head += " Sta SG";
            }
            head += " 3 2 1 . 1 2 3 NB PS";
            pw.println(head);
            
            pw.flush();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        }
    }
    
    /**
     * This method will be called every time that one game is started
     * @param gameNumber the atual game number.
     */
    @Override
    public synchronized void newGame(int gameNumber){
        pw.println("Game " + gameNumber);
        
        String head = "Ref  Coa 1";
        for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
            head += " Cont " + Integer.toString(i);
        }
        head += " Coa 2";
        for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
            head += " Cont " + Integer.toString(i);
        }
        head += "       Trial";
        pw.println(head);

        head = "Sta  Stat";
        for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
            head += " Sta SG";
        }
        head += "  Stat";
        for(int i=1; i<=Match.N_CONTESTANTS/2; i++){
            head += " Sta SG";
        }
        head += " 3 2 1 . 1 2 3 NB PS";
        pw.println(head);
        
        pw.flush();
    }
    
    /**
     * This method will be called to finish write the log file.
     */
    public synchronized void writeEnd(){
        pw.println("\nLegend:");
        pw.println("Ref Sta    – state of the referee");
        pw.println("Coa # Stat - state of the coach of team # (# - 1 .. 2)");
        pw.println("Cont # Sta – state of the contestant # (# - 1 .. "+Integer.toString(Match.N_CONTESTANTS/2)+") of team whose coach was listed to the immediate left");
        pw.println("Cont # SG  – strength of the contestant # (# - 1 .. "+Integer.toString(Match.N_CONTESTANTS/2)+") of team whose coach was listed to the immediate left");
        pw.println("TRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)");
        pw.println("TRIAL – NB – trial number");
        pw.println("TRIAL – PS – position of the centre of the rope at the beginning of the trial");
        pw.flush();
        pw.close();
    }
    
    /**
     * New game and print line with the game.
     */
    @Override
    public synchronized void newGame(){
        match.newGame();
        this.newGame(this.match.getNumberOfGames());
    }
    
    /**
     * New trial with the game.
     */
    @Override
    public synchronized void newTrial(){
        match.newTrial();
    }
    
    /**
     * Number of games played.
     * @return number of games played.
     */
    @Override
    public synchronized int getNumberOfGames(){
        return match.getNumberOfGames();
    }
    
    /**
     * Declare match winner.
     */
    @Override
    public synchronized void declareMatchWinner(){
        pw.println(match.declareMatchWinner());
    }
    
    /**
     * Total number of games.
     * @return total number of games played.
     */
    @Override
    public synchronized int getTotalNumberOfGames(){
        return match.getTotalNumberOfGames();
    }
    
    /**
     * Update the rope only needs the team and contestant because the match has
     * the strengths of each contestant.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void updateRope(String team, int contestant){
        match.updateRope(team, match.getContestantStrength(team, contestant));
    }
    
    /**
     * Assert trial decision.
     * @return decision, can be 2 (B), -2 (A) for knock outs; 0 for game finished with win or draw
     * or 1 for new trial.
     */
    @Override
    public synchronized int assertTrialDecision(){
        return match.assertTrialDecision();
    }
    
    /**
     * Init the contestant with the initial state, team and contestant.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void initContestant(ContestantState state, String team, int contestant){
        this.match.setContestantState(state, team, contestant);
        this.match.setContestantStrength(0, team, contestant);
    }
    
    /**
     * Update the contestant state.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void setContestantState(ContestantState state, String team, int contestant){
        this.match.setContestantState(state, team, contestant);
        this.printLine();
    }
    
    /**
     * Init coach.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    @Override
    public synchronized void initCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
    }
    
    /**
     * Set coach state.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    @Override
    public synchronized void setCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
        this.printLine();
    }
    
    /**
     * Init the referee state.
     * @param state referee state.
     */
    @Override
    public synchronized void initRefereeState(RefereeState state){
        this.match.setRefereeState(state);
    }
    
    /**
     * Set the referee state.
     * @param state referee state.
     */
    @Override
    public synchronized void setRefereeState(RefereeState state){
        this.match.setRefereeState(state);
        this.printLine();
    }
    
    /**
     * Set the contestant last trial, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void setContestantLastTrial(String team, int contestant){
        this.match.setContestantLastTrial(team, contestant);
    }
    
    /**
     * Refresh strengths of the team, the coach calls this method.
     * @param team Team identifier, can be A or B.
     */
    @Override
    public synchronized void refreshStrengths(String team){
        this.match.refreshStrengths(team);
    }
    
    /**
     * Set position, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void setPosition(String team, int contestant){
        this.match.setPosition(team, contestant);
        this.printLine();

    }
    
    /**
     * Remove position of the player.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    @Override
    public synchronized void removePosition(String team, int contestant){
        if(team.equals("A")){
            HashMap<Integer, Integer> tmpA = this.match.getPositionsA();
            for(int i = 1; i<=3; i++){
                if(tmpA.containsKey(i)){
                    if(tmpA.get(i) == contestant){
                        this.match.removePosition(team, i);
                    }
                }
            }
        }else if(team.equals("B")){
            HashMap<Integer, Integer> tmpB = this.match.getPositionsB();
            for(int i = 1; i<=3; i++){
                if(tmpB.containsKey(i)){
                    if(tmpB.get(i) == contestant){
                        this.match.removePosition(team, i);
                    }
                }
            }
        }
        this.printLine();

    }
    
    private void printLine(){
        if(this.match.getCoachStatesN() != N_COACHS ||
            this.match.getContestantsStatesN() != N_CONTESTANTS){
            return;
        }
        
        if(this.match.getNumberOfGames()==0){
            return;
        }
        
        pw.print(this.match.getRefereeState());
        pw.print("  ");
        pw.print(this.match.getCoachState("A"));
        pw.print(" ");
        
        Set<Integer> contestants = this.match.getNumberOfContestants("A");
        
        for(Integer i : contestants){
            pw.print(this.match.getContestantState("A", i));
            pw.print(" ");
            pw.print(this.match.getContestantStrength("A", i));
            pw.print(" ");
        }
            
        pw.print(" ");
        pw.print(this.match.getCoachState("B"));
        pw.print(" ");
        
        for(Integer i : contestants){
            pw.print(this.match.getContestantState("B", i));
            pw.print(" ");
            pw.print(this.match.getContestantStrength("B", i));
            pw.print(" ");
        }
        HashMap<Integer, Integer> positionsA = this.match.getPositionsA();
        HashMap<Integer, Integer> positionsB = this.match.getPositionsB();
        
        String posA = "";
        String posB = " ";
        for(int i = 3; i>=1; i--){
            if(positionsA.containsKey(i)){
                posA += positionsA.get(i).toString() + " ";
                if(positionsA.get(i).toString().length() < 1){
                    posA += " ";
                }
            }else{
                posA += "- ";
            }
        }
        for(int i = 1; i<=3; i++){
            if(positionsB.containsKey(i)){
                posB += positionsB.get(i).toString() + " ";
                if(positionsB.get(i).toString().length() < 1){
                    posB += " ";
                }
            }else{
                posB += "- ";
            }
        }
        pw.printf(posA + "." + posB + "%2d %2d\n", this.match.gameNumberOfTrials(), this.match.getCentre_of_the_rope());
                    
        pw.flush();
    }
    
    /**
     * Print game winner.
     */
    @Override
    public synchronized void printGameWinner(){
        if(this.match.getNumberOfGames() > 0){
            pw.println(this.match.getWinner());
        }
    }
    
    @Override 
    public void finished(){
        numberEntitiesRunning--;
        if (numberEntitiesRunning > 0){
            return;
        }
        terminateServers();
    }
    
    public void terminateServers(){
        RegisterInterface reg = null;
        Registry registry = null;
        
        RegistryConfig rc = new RegistryConfig("config.ini");
        String rmiRegHostName = rc.registryHost();
        int rmiRegPortNumb = rc.registryPort();
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }

        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.logNameEntry;
        
        // shutdown playground
        try
        {
            PlaygroundInterface play = (PlaygroundInterface) registry.lookup (RegistryConfig.playgroundNameEntry);
            play.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating playground: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Playground is not registered: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // shutdown referee site
        try
        {
            RefereeSiteInterface refInt = (RefereeSiteInterface) registry.lookup (RegistryConfig.refereeSiteNameEntry);
            refInt.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating referee site: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Referee Site is not registered: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // shutdown bench
        try
        {
            BenchInterface whInt = (BenchInterface) registry.lookup (RegistryConfig.benchNameEntry);
            whInt.signalShutdown();
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating bench: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Bench is not registered: " + e.getMessage () + "!");
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // shutdown log
        
        try {
            reg = (RegisterInterface) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            // Unregister ourself
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Log registration exception: " + e.getMessage());
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotBoundException e) {
            System.out.println("Log not bound exception: " + e.getMessage());
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        writeEnd();
        
        System.out.println("Log closed.");
    }
}
