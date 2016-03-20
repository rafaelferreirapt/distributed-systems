/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.CoachState;
import entities.ContestantState;
import entities.RefereeState;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Match {
    
    private final Game[] games;
    private int game = 0;
    private final int number_of_games = 1000;
    private final int pontuation[];
    
    private final HashMap<String, HashMap<Integer, Integer>> strengths;
    private final HashMap<String, HashMap<Integer, ContestantState>> contestants_states;
    private final HashMap<String, CoachState> coach_states;
    private RefereeState referee_state;
    
    private int trials_played = 0;
    
    private static Match instance = null;
    
    private Match() {
        this.strengths = new HashMap<>();
        this.contestants_states = new HashMap<>();
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
    
    public synchronized void setContestantStrength(int strength, String team, int contestant){
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
    
    public void newTrial(int centre_of_the_rope){
        this.games[(game-1)].newTrial(centre_of_the_rope);
        
    }
    
    public int gameNumberOfTrials(){
        return this.games[(game-1)].gameNumberOfTrials();
    }
    
    public int getNumberOfGames(){
        return this.game;
    }
    
    public void declareMatchWinner(){
        this.updatePontuation();

        if(this.pontuation[0] > this.pontuation[1]){
            //A
            System.err.println("Match was won by team A ("+this.pontuation[0]+"-"+this.pontuation[1]+").");
        }else if(this.pontuation[0] < this.pontuation[1]){
            // B
            System.err.println("Match was won by team B ("+this.pontuation[0]+"-"+this.pontuation[1]+").");
        }else{
            // Draw
            System.err.println("Match was a draw.");
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
}
