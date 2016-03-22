/*
 * Distributed Systems
 * Rafael Ferreira and Rodrigo Cunha
 */
package general_info_repo;

/**-
 *
 * @author António Ferreira, 67405; Rodrigo Cunha, 67800
 */
public class Game {
    
    private final int id;
    private String winner;
    private char winner_short;
    private final Trial[] trials;
    private int trial_idx = 0;
    private final int[] pontuation;
    private int centre_of_the_rope = 0;
    
    /**
     * The game id, is the number of game of the match.
     * @param id game identifier, game number in the match.
     */
    public Game(int id){
        this.trials = new Trial[6];
        this.id = id;
        this.pontuation = new int[2];
        this.pontuation[0] = 0;
        this.pontuation[1] = 0;
    }
    
    /**
     * New trial of the game.
     */
    public void newTrial(){
        assert trial_idx < this.trials.length;
        if(trial_idx!=0){
            this.centre_of_the_rope = this.trials[trial_idx-1].getRope();
        }
        this.trials[trial_idx++] = new Trial(getCentre_of_the_rope());
    }
    
    /**
     * Update the center of the rope with the strength and team.
     * @param team can be "A" or "B"
     * @param strength will be used to push the rope.
     */
    public void updateRope(String team, int strength){
        this.trials[trial_idx-1].updateRope(team, strength);
    }
    
    /**
     * This method will determine if one game is wonned by team A or B or if
     * is a draw.
     * @return decision, can be 2 (B), -2 (A) for knock outs; 0 for game finished with win or draw
     * or 1 for new trial.
     */
    public int assertTrialDecision(){
        int rope = this.trials[trial_idx-1].getRope();
        
        if(rope >= 4){
            this.winner = "Game "+(this.id+1) +" was won by team B by knock out in " + trial_idx + " trials.";
            this.winner_short = 'B';
            return 2;
        }else if(rope <= -4){
            this.winner = "Game "+(this.id+1)+" was won by team A by knock out in " + trial_idx + " trials.";
            this.winner_short = 'A';
            return -2;
        }else if(rope > 0){
            this.pontuation[1] += 1;
        }else if(rope < 0){
            this.pontuation[0] += 1;
        }
        
        if(trial_idx >= 6){
            if(this.pontuation[0] > this.pontuation[1]){
                // A
                this.winner = "Game "+(this.id+1)+" was won by team A by points.";
                this.winner_short = 'A';
            }else if(this.pontuation[0] < this.pontuation[1]){
                // B
                this.winner = "Game "+(this.id+1)+" was won by team B by points.";
                this.winner_short = 'B';
            }else{
                // draw
                this.winner = "Game "+(this.id+1)+" was a draw.";
                this.winner_short = 'D';
            }
            return 0;
        }else{
            this.newTrial();
            return 1;
        }
    }
    
    /**
     * Winner of the game.
     * @return game winner.
     */
    public char getWinner(){
        return this.winner_short;
    }
    
    /**
     * Get the log winner String.
     * @return log winner string.
     */
    public String getWinnerString(){
        return this.winner;
    }
    
    /**
     * Number of trials played in the game.
     * @return game number of trials.
     */
    public int gameNumberOfTrials(){
        return this.trial_idx;
    }

    /**
     * Get the centre of the rope in that game.
     * @return centre of the rope in that game.
     */
    public int getCentre_of_the_rope() {
        return centre_of_the_rope;
    }
}
