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
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The log will be the gateway to all the information of the match, games, trials,
 * strengths of the players. It will be responsible too for write in one file with
 * the log of the match.
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
     * @param filename where the log will be saved.
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
    
    /**
     * This is a singleton, this is important to return the Log instance.
     * @return log intance, this is a singleton.
     */
    public synchronized static Log getInstance(){
        return instance;
    }
    
    /**
     * This method writes the head of the logging file.
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
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method will be called every time that one game is started
     * @param gameNumber the atual game number.
     */
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
     * This method will be called to finish write the logging file.
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
    public synchronized void newGame(){
        match.newGame();
        this.newGame(this.match.getNumberOfGames());
    }
    
    /**
     * New trial with the game.
     */
    public synchronized void newTrial(){
        match.newTrial();
    }
    
    /**
     * Game number of trials played.
     * @return game number of trials played.
     */
    public synchronized int gameNumberOfTrials(){
        return match.gameNumberOfTrials();
    }
    
    /**
     * Number of games played.
     * @return number of games played.
     */
    public synchronized int getNumberOfGames(){
        return match.getNumberOfGames();
    }
    
    /**
     * Declare match winner.
     */
    public synchronized void declareMatchWinner(){
        pw.println(match.declareMatchWinner());
    }
    
    /**
     * Total number of games.
     * @return total number of games played.
     */
    public synchronized int getTotalNumberOfGames(){
        return match.getTotalNumberOfGames();
    }
    
    /**
     * Update the rope only needs the team and contestant because the match has
     * the strengths of each contestant.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    public synchronized void updateRope(String team, int contestant){
        this.match.updateRope(team, this.match.getContestantStrength(team, contestant));
    }
    
    /**
     * Assert trial decision.
     * @return decision, can be 2 (B), -2 (A) for knock outs; 0 for game finished with win or draw
     * or 1 for new trial.
     */
    public synchronized int assertTrialDecision(){
        return this.match.assertTrialDecision();
    }
    
    /**
     * Number of trials played.
     * @return number of trials played
     */
    public synchronized int getTrials_played() {
        return match.getTrials_played();
    }
    
    /**
     * Init the contestant with the initial state, team and contestant.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
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
    public synchronized void setContestantState(ContestantState state, String team, int contestant){
        this.match.setContestantState(state, team, contestant);
        this.printLine();
    }
    
    /**
     * Init coach.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    public synchronized void initCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
    }
    
    /**
     * Set coach state.
     * @param team Team identifier, can be A or B.
     * @param state coach state.
     */
    public synchronized void setCoachState(String team, CoachState state){
        this.match.setCoachState(team, state);
        this.printLine();
    }
    
    /**
     * Init the referee state.
     * @param state referee state.
     */
    public synchronized void initRefereeState(RefereeState state){
        this.match.setRefereeState(state);
    }
    
    /**
     * Set the referee state.
     * @param state referee state.
     */
    public synchronized void setRefereeState(RefereeState state){
        this.match.setRefereeState(state);
        this.printLine();
    }
    
    /**
     * Get the contestant last trial, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     * @return
     */
    public synchronized int getContestantLastTrial(String team, int contestant){
        return this.match.getContestantLastTrial(team, contestant);
    }
    
    /**
     * Set the contestant last trial, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    public synchronized void setContestantLastTrial(String team, int contestant){
        this.match.setContestantLastTrial(team, contestant);
    }
    
    /**
     * Refresh strengths of the team, the coach calls this method.
     * @param team Team identifier, can be A or B.
     */
    public synchronized void refreshStrengths(String team){
        this.match.refreshStrengths(team);
    }
    
    /**
     * Set position, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    public synchronized void setPosition(String team, int contestant){
        this.match.setPosition(team, contestant);
    }
    
    /**
     * Remove position of the player.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
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
    public synchronized void printGameWinner(){
        if(this.match.getNumberOfGames() > 0){
            pw.println(this.match.getWinner());
        }
    }
    
}
