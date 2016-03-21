/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.CoachState;
import entities.ContestantState;
import entities.RefereeState;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Match {
    
    private final Game[] games;
    private int game = 0;
    private final int number_of_games = 100;
    private final int pontuation[];
    private int positionA = 0;
    private int positionB = 0;
    
    private static final int MAX_STRENGTH = 29;
    private static final int MIN_STRENGTH = 20;
    
    private final HashMap<String, HashMap<Integer, Integer>> strengths;
    private final HashMap<Integer, Integer> positionsA;
    private final HashMap<Integer, Integer> positionsB;
    private final HashMap<String, HashMap<Integer, Integer>> contestant_last_trial;
    private final HashMap<String, HashMap<Integer, ContestantState>> contestants_states;
    private final HashMap<String, CoachState> coach_states;
    private RefereeState referee_state;
    
    private int trials_played = 0;
    
    private static Match instance = null;
    
    private Match() {
        this.strengths = new HashMap<>();
        this.positionsA = new HashMap<>();
        this.positionsB = new HashMap<>();
        this.contestants_states = new HashMap<>();
        this.contestant_last_trial = new HashMap<>();
        this.coach_states = new HashMap<>();
        
        this.games = new Game[number_of_games];
        this.pontuation = new int[2];   
        this.pontuation[0] = this.pontuation[1] = 0;
    }
    
    public static Match getInstance() {
       if(instance == null) {
          instance = new Match();
       }
       return instance;
    }
    
    public synchronized void setContestantState(ContestantState state, String team, int contestant){
        if(this.contestants_states.containsKey(team)){
            if(this.contestants_states.get(team).containsKey(contestant)){
                this.contestants_states.get(team).replace(contestant, state);
            }else{
                this.contestants_states.get(team).put(contestant, state);
            }
        }else{
            this.contestants_states.put(team, new HashMap<>());
            this.contestants_states.get(team).put(contestant, state);
        }
    }
    
    public synchronized void setCoachState(String team, CoachState state){
        if(this.coach_states.containsKey(team)){
            this.coach_states.replace(team, state);
        }else{
            this.coach_states.put(team, state);
        }
    }
    
    public synchronized void setRefereeState(RefereeState state){
        this.referee_state = state;
    }
    
    public synchronized void setPosition(String team, int contestant){
        if(team.equals("A")){
            this.positionA++;
            this.positionsA.put(this.positionA, contestant);
        }else if(team.equals("B")){
            this.positionB++;
            this.positionsB.put(this.positionB, contestant);
        }
    }
    
    public synchronized void removePosition(String team, int position){
        if(team.equals("A")){
            if(this.positionsA.containsKey(position)){
                this.positionsA.remove(position);
                this.positionA--;
            }
            
        }else if(team.equals("B")){
            if(this.positionsB.containsKey(position)){
                this.positionsB.remove(position);
                this.positionB--;
            }
        }
    }
    
    public synchronized HashMap getPositionsA(){
        return this.positionsA;
    }
    
    public synchronized HashMap getPositionsB(){
        return this.positionsB;
    }
    
    public synchronized void setContestantStrength(int strength, String team, int contestant){
        if(strength > 30){
            strength = 30;
        }else if(strength == 0){
            strength = MIN_STRENGTH + (int)Math.ceil(Math.random() * (MAX_STRENGTH - MIN_STRENGTH) + 1);
        }else if(strength < 20){
            strength = 20;
        }
        
        if(this.strengths.containsKey(team)){
            if(this.strengths.get(team).containsKey(contestant)){
                this.strengths.get(team).replace(contestant, strength);
            }else{
                this.strengths.get(team).put(contestant, strength);
            }
        }else{
            this.strengths.put(team, new HashMap<>());
            this.strengths.get(team).put(contestant, strength);
        }
    }
    
    public void newGame(){
        assert game < this.games.length;
        
        this.updatePontuation();
        
        this.games[game] = new Game(game++);
    }
    
    public void newTrial(){
        this.games[(game-1)].newTrial();
        
    }
    
    public int gameNumberOfTrials(){
        return this.games[(game-1)].gameNumberOfTrials();
    }
    
    public int getNumberOfGames(){
        return this.game;
    }
    
    public String declareMatchWinner(){
        this.updatePontuation();

        if(this.pontuation[0] > this.pontuation[1]){
            //A
            return "Match was won by team A ("+this.pontuation[0]+"-"+this.pontuation[1]+").";
        }else if(this.pontuation[0] < this.pontuation[1]){
            // B
            return "Match was won by team B ("+this.pontuation[0]+"-"+this.pontuation[1]+").";
        }else{
            // Draw
            return "Match was a draw.";
        }
    }
    
    public int getTotalNumberOfGames(){
        return this.number_of_games;
    }
    
    public void updateRope(String team, int strength){
        this.games[(game-1)].updateRope(team, strength);
    }
    
    public int assertTrialDecision(){
        this.trials_played++;
        return this.games[(game-1)].assertTrialDecision();
    }
    
    private void updatePontuation(){
        if(game!=0){
            char winner = this.games[game-1].getWinner();
            if(winner=='A'){
                this.pontuation[0]++;
            }else if(winner=='B'){
                this.pontuation[1]++;
            }
        }
    }

    public int getTrials_played() {
        return trials_played;
    }
    
    public synchronized RefereeState getRefereeState() {
        return referee_state;
    }
    
    public synchronized CoachState getCoachState(String team){
        return this.coach_states.get(team);
    }
    
    public synchronized ContestantState getContestantState(String team, int contestant){
        return this.contestants_states.get(team).get(contestant);
    }
    
    public synchronized Set<Integer> getNumberOfContestants(String team){
        return this.contestants_states.get(team).keySet();
    }
    
    public synchronized int getContestantStrength(String team, int contestant){
        return this.strengths.get(team).get(contestant);
    }

    public int getCentre_of_the_rope() {
        return this.games[(game-1)].getCentre_of_the_rope();
    }
    
    public synchronized int getContestantLastTrial(String team, int contestant){
        if(!this.contestant_last_trial.get(team).containsKey(contestant)){
            return 0;
        }
        return this.contestant_last_trial.get(team).get(contestant);
    }
    
    public String getWinner(){
        return this.games[(game-1)].getWinnerString();
    }
    
    public synchronized void refreshStrengths(String team){
        for(Entry<Integer, Integer> entry : this.strengths.get(team).entrySet()) {
            int strength = entry.getValue();
            
            if(this.getContestantLastTrial(team, entry.getKey()) == this.trials_played){
                strength--;
            }else{
                strength++;
            }
            
            this.setContestantStrength(strength, team, entry.getKey());
        }
    }
    
    public synchronized void setContestantLastTrial(String team, int contestant){
        int last_trial = this.getTrials_played() + 1;
        
        if(this.contestant_last_trial.containsKey(team)){
            if(this.contestant_last_trial.get(team).containsKey(contestant)){
                this.contestant_last_trial.get(team).replace(contestant, last_trial);
            }else{
                this.contestant_last_trial.get(team).put(contestant, last_trial);
            }
        }else{
            this.contestant_last_trial.put(team, new HashMap<>());
            this.contestant_last_trial.get(team).put(contestant, last_trial);
        }
    }
}
