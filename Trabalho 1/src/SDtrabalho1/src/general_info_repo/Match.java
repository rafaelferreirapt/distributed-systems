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
 * The match singleton will have games, positions, strengths, contestants last trials,
 * contestant states, coach states, referee state, number of trials played.
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Match {
    
    private final Game[] games;
    private int game = 0;
    private final int number_of_games = 3;
    private final int pontuation[];
    private int positionA = 0;
    private int positionB = 0;
    
    private static final int MAX_STRENGTH = 24;
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
    
    /**
     * The match is a singleton.
     * @return Match instance, is a singleton.
     */
    public static Match getInstance() {
       if(instance == null) {
          instance = new Match();
       }
       return instance;
    }
    
    /**
     * Update the contestant state.
     * @param state contestant state.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
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
    
    /**
     * Set coach state.
     * @param team Team identifier, can be A or B.
     * @param state contestant state.
     */
    public synchronized void setCoachState(String team, CoachState state){
        if(this.coach_states.containsKey(team)){
            this.coach_states.replace(team, state);
        }else{
            this.coach_states.put(team, state);
        }
    }
    
    /**
     * Set the referee state.
     * @param state contestant state.
     */
    public synchronized void setRefereeState(RefereeState state){
        this.referee_state = state;
    }
    
    /**
     * Set position, we only need the team and the contestant id.
     * @param team Team identifier, can be A or B.
     * @param contestant The contestant ID.
     */
    public synchronized void setPosition(String team, int contestant){
        if(team.equals("A")){
            this.positionA++;
            this.positionsA.put(this.positionA, contestant);
        }else if(team.equals("B")){
            this.positionB++;
            this.positionsB.put(this.positionB, contestant);
        }
    }
    
    /**
     * Remove position of the player.
     * @param team Team identifier, can be A or B.
     * @param position The position index.
     */
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
    
    /**
     * Positions A.
     * @return HashMap with team A positions.
     */
    public synchronized HashMap getPositionsA(){
        return this.positionsA;
    }
    
    /**
     * Positions B.
     * @return HashMap with team B positions.
     */
    public synchronized HashMap getPositionsB(){
        return this.positionsB;
    }
    
    /**
     * Set contestant strength.
     * @param strength contestant strength.
     * @param team "A" or "B"
     * @param contestant ID.
     */
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
    
    /**
     * New game and print line with the game.
     */
    public void newGame(){
        assert game < this.games.length;
        
        this.updatePontuation();
        
        this.games[game] = new Game(game++);
    }
    
    /**
     * New trial with the game.
     */
    public void newTrial(){
        this.games[(game-1)].newTrial();
        
    }
    
    /**
     * Game number of trials played.
     * @return number of trials.
     */
    public int gameNumberOfTrials(){
        return this.games[(game-1)].gameNumberOfTrials();
    }
    
    /**
     * Number of games played.
     * @return number of games.
     */
    public int getNumberOfGames(){
        return this.game;
    }
    
    /**
     * Declare match winner.
     * @return match winner.
     */
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
    
    /**
     * Total number of games.
     * @return total number of games.
     */
    public int getTotalNumberOfGames(){
        return this.number_of_games;
    }
    
    /**
     * Update the rope only needs the team and contestant because the match has
     * the strengths of each contestant.
     * @param team
     * @param strength
     */
    public void updateRope(String team, int strength){
        this.games[(game-1)].updateRope(team, strength);
    }
    
    /**
     * Assert trial decision.
     * @return trial decision.
     */
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

    /**
     * Number of trials played.
     * @return trials played.
     */
    public int getTrials_played() {
        return trials_played;
    }
    
    /**
     * Get referee state.
     * @return referee state.
     */
    public synchronized RefereeState getRefereeState() {
        return referee_state;
    }
    
    /**
     * Get coach state.
     * @param team "A" or "B"
     * @return coach state
     */
    public synchronized CoachState getCoachState(String team){
        return this.coach_states.get(team);
    }
    
    /**
     * Get contestant state.
     * @param team "A" or "B"
     * @param contestant ID
     * @return contestant state
     */
    public synchronized ContestantState getContestantState(String team, int contestant){
        return this.contestants_states.get(team).get(contestant);
    }
    
    /**
     * Get number of contestants.
     * @param team "A" or "B"
     * @return number of contestants
     */
    public synchronized Set<Integer> getNumberOfContestants(String team){
        return this.contestants_states.get(team).keySet();
    }
    
    /**
     * Get contestant strength.
     * @param team "A" or "B"
     * @param contestant ID
     * @return strength of contestant
     */
    public synchronized int getContestantStrength(String team, int contestant){
        return this.strengths.get(team).get(contestant);
    }

    /**
     * Get centre of the rope.
     * @return centre of the rope.
     */
    public int getCentre_of_the_rope() {
        return this.games[(game-1)].getCentre_of_the_rope();
    }
    
    /**
     * Get contestant last trial.
     * @param team "A" or "B"
     * @param contestant ID
     * @return last trial of the contestant.
     */
    public synchronized int getContestantLastTrial(String team, int contestant){
        if(!this.contestant_last_trial.get(team).containsKey(contestant)){
            return 0;
        }
        return this.contestant_last_trial.get(team).get(contestant);
    }
    
    /**
     * Get winner.
     * @return game winner.
     */
    public String getWinner(){
        return this.games[(game-1)].getWinnerString();
    }
    
    /**
     * Refresh strengths.
     * @param team "A" or "B"
     */
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
    
    /**
     * Set contestant last trial.
     * @param team "A" or "B"
     * @param contestant ID
     */
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
