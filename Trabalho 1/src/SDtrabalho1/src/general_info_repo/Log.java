/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import com.sun.javafx.binding.Logging;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
     * Blocking variable, when this variable is true, 
     * no more instances of this class can be instantiated
     */
    private static boolean canBeInstantiated = true;
    
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
    }
    
    /**
     * 
     */
    public synchronized void newTrial(){
        match.newTrial(0);
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
        match.declareMatchWinner();
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
     * @param strength
     */
    public synchronized void updateRope(String team, int strength){
        this.match.updateRope(team, strength);
    }
    
    /**
     * 
     * @return 
     */
    public synchronized int assertTrialDecision(){
        return this.match.assertTrialDecision();
    }
    
    public synchronized int getTrials_played() {
        return match.getTrials_played();
    }
    
}
