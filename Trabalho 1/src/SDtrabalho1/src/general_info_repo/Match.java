/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

import entities.CoachState;
import entities.ContestantState;
import entities.RefereeState;
import java.util.HashMap;

/**
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Match {
    
    private Game[] games;
    private int game = 0;
    private final int number_of_games = 1000;
    private int pontuation[];
    
    private HashMap<String, Integer> strengths;
    private HashMap<HashMap<String, Integer>, ContestantState> contestants_states;
    private HashMap<HashMap<String, Integer>, CoachState> coach_states;
    private RefereeState referee_states;
    
    public int trials_played = 0;
    
    private static Match instance = null;
    
    private Match() {
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
}
