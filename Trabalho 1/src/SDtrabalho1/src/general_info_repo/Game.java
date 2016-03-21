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
    
    private int id;
    private String winner;
    private char winner_short;
    private Trial[] trials;
    private int trial_idx = 0;
    private int[] pontuation;
    private int centre_of_the_rope = 0;
    
    public Game(int id){
        this.trials = new Trial[6];
        this.id = id;
        this.pontuation = new int[2];
        this.pontuation[0] = 0;
        this.pontuation[1] = 0;
    }
    
    public void newTrial(){
        assert trial_idx < this.trials.length;
        if(trial_idx!=0){
            this.centre_of_the_rope = this.trials[trial_idx-1].getRope();
        }
        this.trials[trial_idx] = new Trial(trial_idx++, getCentre_of_the_rope());
    }
    
    public void updateRope(String team, int strength){
        this.trials[trial_idx-1].updateRope(team, strength);
    }
    
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
    
    public char getWinner(){
        return this.winner_short;
    }
    
    public String getWinnerString(){
        return this.winner;
    }
    
    public int gameNumberOfTrials(){
        return this.trial_idx;
    }

    public int getCentre_of_the_rope() {
        return centre_of_the_rope;
    }
}
