/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import com.sun.javafx.binding.Logging;
import entities.CoachState;
import entities.ContestantState;
import entities.RefereeState;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Log {
    
    private final Match match = Match.getInstance();
    
    /**
     *  File where the log will be saved
     */
    private final File log;
    
    private static PrintWriter pw;
    
    /**
     * This will be a singleton
     */
    private static Log instance = null;
    
    /**
     * This will instantiate the log Class
     * Name of the file where the log will be saved
     * @param filename 
     */
    private Log(String filename){
        if(filename.length()==0){
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
            filename = "GameOfTheRope_" + date.format(today) + ".log";
        }
        this.log = new File(filename);
    }
    
    /**
     * This static constructor is used to init the log file, this is a singleton and we 
     * need to specify what is the filename, so we created this method that
     * will allow to instantiate the singleton.
     */
    static{
        instance = new Log("");
        instance.writeInit();
    }
    
    public synchronized static Log getInstance(){
        return instance;
    }
    
    /**
     * This method writes the head of the logging file
     */
    private void writeInit(){
        try{
            pw = new PrintWriter(log);
            pw.println("                               Game of the Rope - Description of the internal state");
            pw.println("Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
            pw.println("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *  to be done... 
     * @param line 
     */
    public synchronized void writeLine(String line){
        
    }
    
    /**
     * This method will be called every time that one game is started
     * @param gameNumber 
     */
    public synchronized void newGame(int gameNumber){
        pw.println("Game " + gameNumber);
        pw.println("Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
        pw.println("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG  Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
        pw.flush();
    }
    
    /**
     * This method will be called to finish write the logging file
     */
    public synchronized void writeEnd(){
        pw.println("\nLegend:");
        pw.println("Ref Sta    – state of the referee");
        pw.println("Coa # Stat - state of the coach of team # (# - 1 .. 2)");
        pw.println("Cont # Sta – state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left");
        pw.println("Cont # SG  – strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left");
        pw.println("TRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)");
        pw.println("TRIAL – NB – trial number");
        pw.println("TRIAL – PS – position of the centre of the rope at the beginning of the trial");
        pw.flush();
        pw.close();
    }
    
    /**
     * 
     */
    public synchronized void newGame(){
        match.newGame();
        this.newGame(this.match.getNumberOfGames());
    }
    
    /**
     * 
     */
    public synchronized void newTrial(){
        match.newTrial();
    }
    
    /**
     * 
     * @return 
     */
    public synchronized int gameNumberOfTrials(){
        return match.gameNumberOfTrials();
    }
    
    /**
     * 
     * @return 
     */
    public synchronized int getNumberOfGames(){
        return match.getNumberOfGames();
    }
    
    /**
     * 
     */
    public synchronized void declareMatchWinner(){
        pw.println(match.declareMatchWinner());
    }
    
    /**
     * 
     * @return 
     */
    public synchronized int getTotalNumberOfGames(){
        return match.getTotalNumberOfGames();
    }
    
    /**
     * 
     * @param team
     * @param contestant
     */
    public synchronized void updateRope(String team, int contestant){
        this.match.updateRope(team, this.match.getContestantStrength(team, contestant));
    }
    
    /**
     * 
     * @return 
     */
    public synchronized int assertTrialDecision(){
        int decision = this.match.assertTrialDecision();
        
        /*switch(decision){
            case 2:
            case -2:
            case 0:
                pw.println(this.match.getWinner());
                break;
        }*/
        
        return decision;
    }
    
    public synchronized int getTrials_played() {
        return match.getTrials_played();
    }
    
    public synchronized void initContestant(ContestantState state, String team, int contestant){
        this.match.setContestantState(state, team, contestant);
        this.match.setContestantStrength(0, team, contestant);
    }
    
    public synchronized void setContestantState(ContestantState state, String team, int contestant){
        this.match.setContestantState(state, team, contestant);
        this.printLine();
    }
    
    public synchronized void initCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
    }
    
    public synchronized void setCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
        this.printLine();
    }
    
    public synchronized void initRefereeState(RefereeState state){
        this.match.setRefereeState(state);
    }
    
    public synchronized void setRefereeState(RefereeState state){
        this.match.setRefereeState(state);
        this.printLine();
    }
    
    public synchronized int getContestantLastTrial(String team, int contestant){
        return this.match.getContestantLastTrial(team, contestant);
    }
    
    public synchronized void setContestantLastTrial(String team, int contestant){
        this.match.setContestantLastTrial(team, contestant);
    }
    
    public synchronized void refreshStrengths(String team){
        this.match.refreshStrengths(team);
    }
    
    private void printLine(){
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
        
        pw.printf("- - - . - - - %2d %2d\n", this.match.gameNumberOfTrials(), this.match.getCentre_of_the_rope());
                    
        pw.flush();
    }
    
    public synchronized void printGameWinner(){
        if(this.match.getNumberOfGames() > 0){
            pw.println(this.match.getWinner());
        }
    }
    
    public synchronized void printMatchWinner(){
        
    }
    
}
